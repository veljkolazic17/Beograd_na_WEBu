package rs.psi.beogradnawebu.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;
import java.security.Principal;



@Controller
public class LikeController {
    private static final Logger log = LoggerFactory.getLogger(FilterController.class);

    private final SmestajDAO smestajDAO;
    private final KorisnikDAO korisnikDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final MMLVRecommenderImpl mmlvRecommender;
    public LikeController(SmestajDAO smestajDAO,KorisnikDAO korisnikDAO,LajkSmestajaCDAO lajkSmestajaCDAO,MMLVRecommenderImpl mmlvRecommender){
        this.smestajDAO = smestajDAO;
        this.korisnikDAO = korisnikDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        this.mmlvRecommender = mmlvRecommender;
    }
    @PostMapping("/like/{idSmestaj}")
    public String likeSmestaj(Principal principal,@PathVariable Integer idSmestaj){
        Korisnik k =korisnikDAO.getUserByUsername(principal.getName()).orElse(null);
        Smestaj s = smestajDAO.get(idSmestaj).orElse(null);
        if(s == null || k == null) return "redirect:/pregledsmestaja";
        //Updatuj tezine za korisnika k na osnovu stana s;
        mmlvRecommender.update(k,s);
        LajkSmestaja l = new LajkSmestaja();
        l.setIdkorisnik(k.getIdkorisnik());
        l.setIdsmestaj(s.getIdsmestaj());
        lajkSmestajaCDAO.create(l);
        s.setBrojLajkova(s.getBrojLajkova()+1);
        smestajDAO.update(s,idSmestaj);
        return "redirect:/pregledsmestaja";

    }
    @PostMapping("/unlike/{idSmestaj}")
    public String unlikeSmestaj(Principal principal,@PathVariable Integer idSmestaj){
        Korisnik k = korisnikDAO.getUserByUsername(principal.getName()).orElse(null);
        Smestaj s = smestajDAO.get(idSmestaj).orElse(null);
        if(s != null && k!=null) {
            s.setBrojLajkova(s.getBrojLajkova() - 1);
            smestajDAO.update(s, idSmestaj);
            lajkSmestajaCDAO.delete(new int[]{(int) k.getIdkorisnik(), idSmestaj});
        }
        else {
        log.info("Error!");
        }
        return "redirect:/pregledsmestaja";

    }
}
