package rs.psi.beogradnawebu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;

import javax.management.openmbean.OpenMBeanInfoSupport;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/userdata")
public class UserDataController {

    private final KorisnikDAO korisnikDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final RecAlgDAO recAlgDAO;
    private final SmestajDAO smestajDAO;

    public UserDataController(KorisnikDAO korisnikDAO, LajkSmestajaCDAO lajkSmestajaCDAO,RecAlgDAO recAlgDAO,SmestajDAO smestajDAO) {
        this.korisnikDAO = korisnikDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        this.recAlgDAO = recAlgDAO;
        this.smestajDAO = smestajDAO;
    }

    @PostMapping
    public ResponseEntity<String> resetData(@AuthenticationPrincipal User user) {
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).orElse(null);
        if(korisnik != null){
            int idkorisnik = (int)korisnik.getIdkorisnik();
            smestajDAO.decLikes((int) korisnik.getIdkorisnik());
            lajkSmestajaCDAO.deleteLikes((int) korisnik.getIdkorisnik());
            recAlgDAO.delete(idkorisnik);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}