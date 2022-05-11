package rs.psi.beogradnawebu.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.misc.FilterForm;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;


@Controller
public class LikeController {
    private static final Logger log = LoggerFactory.getLogger(FilterController.class);

    private final SmestajDAO smestajDAO;
    private final KorisnikDAO korisnikDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    public LikeController(SmestajDAO smestajDAO,KorisnikDAO korisnikDAO,LajkSmestajaCDAO lajkSmestajaCDAO){
        this.smestajDAO = smestajDAO;
        this.korisnikDAO = korisnikDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
    }
    @PostMapping("/like/{idSmestaj}")
    public String likeSmestaj(Principal principal,@PathVariable Integer idSmestaj){
        //Korisnik k =korisnikDAO.getByUsername(principal.getName()).orElse(null);
        Smestaj s = smestajDAO.get(idSmestaj).orElse(null);
       // if(s!=null && k!=null) {
            LajkSmestaja l = new LajkSmestaja();
            l.setIdkorisnik(1);
            //l.setIdkorisnik(k.getIdkorisnik());
            l.setIdsmestaj(s.getIdsmestaj());
            lajkSmestajaCDAO.create(l);
        s.setBrojLajkova(s.getBrojLajkova()+1);
        smestajDAO.update(s,idSmestaj);
        //}
        //else {
            log.info("Error!");
        //}
        return "redirect:/pregledsmestaja";

    }
    @PostMapping("/unlike/{idSmestaj}")
    public String unlikeSmestaj(Principal principal,@PathVariable Integer idSmestaj){
        //Korisnik k =korisnikDAO.getByUsername(principal.getName()).orElse(null);
        // if(s!=null && k!=null) {
        Smestaj s =smestajDAO.get(idSmestaj).orElse(null);
        s.setBrojLajkova(s.getBrojLajkova() - 1);
        smestajDAO.update(s,idSmestaj);
        lajkSmestajaCDAO.delete(new int[]{1,idSmestaj});

        //l.setIdkorisnik(k.getIdkorisnik());
        //}
        //else {
        log.info("Error!");
        //}
        return "redirect:/pregledsmestaja";

    }
}
