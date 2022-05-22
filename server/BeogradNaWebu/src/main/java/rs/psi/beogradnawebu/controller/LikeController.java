package rs.psi.beogradnawebu.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;

import javax.websocket.server.PathParam;
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
    @GetMapping("/isliked/{username}/{idsmestaj}")
    public String isLiked(@PathParam("username") String username, @PathParam("idsmestaj") Integer idsmestaj, RedirectAttributes redirectAttributes){
        Korisnik k = korisnikDAO.getUserByUsername(username).orElse(null);
        if(k==null)return "redirect:/pregledsmestaja";
        int idkorisnik = (int)k.getIdkorisnik();
        LajkSmestaja lajkSmestaja = lajkSmestajaCDAO.get(new int[]{idkorisnik,idsmestaj}).orElse(null);
        redirectAttributes.addFlashAttribute("isliked",lajkSmestaja != null);
        return "redirect:/pregledsmestaja";
    }
    @PostMapping("/like/{idSmestaj}")
    public String likeSmestaj(@AuthenticationPrincipal User korisnik, @PathVariable Integer idSmestaj){
        Korisnik k =korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);
        Smestaj s = smestajDAO.get(idSmestaj).orElse(null);
        //Updatuj tezine za korisnika k na osnovu stana s;
        mmlvRecommender.update(k,s);
        if(s!=null && k!=null) {
            LajkSmestaja l = new LajkSmestaja();
            l.setIdkorisnik(k.getIdkorisnik());
            l.setIdsmestaj(s.getIdsmestaj());
            lajkSmestajaCDAO.create(l);
            s.setBrojLajkova(s.getBrojLajkova()+1);
            smestajDAO.update(s,idSmestaj);
        }
        else {
            log.info("Error!");
        }
        return "redirect:/pregledsmestaja";

    }
    @PostMapping("/unlike/{idSmestaj}")
    public String unlikeSmestaj(@AuthenticationPrincipal User korisnik,@PathVariable Integer idSmestaj){
        Korisnik k =korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);
        Smestaj s =smestajDAO.get(idSmestaj).orElse(null);
        if(s!=null && k!=null) {
            s.setBrojLajkova(s.getBrojLajkova() - 1);
            smestajDAO.update(s,idSmestaj);
            lajkSmestajaCDAO.delete(new int[]{(int) k.getIdkorisnik(),idSmestaj});

        }
        else {
            log.info("Error!");
        }
        return "redirect:/pregledsmestaja";

    }
}
