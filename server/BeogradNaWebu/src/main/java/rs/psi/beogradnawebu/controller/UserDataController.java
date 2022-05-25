package rs.psi.beogradnawebu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.mailService.MailService;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.passwordGenerator.SimplePasswordGenerator;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;

import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.security.enterprise.credential.Password;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserDataController {

    private final KorisnikDAO korisnikDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final RecAlgDAO recAlgDAO;
    private final SmestajDAO smestajDAO;
    private final MailService mailService;
    private final SimplePasswordGenerator simplePasswordGenerator;

    public UserDataController(
            KorisnikDAO korisnikDAO,
            LajkSmestajaCDAO lajkSmestajaCDAO,
            RecAlgDAO recAlgDAO,
            SmestajDAO smestajDAO,
            MailService mailService,
            SimplePasswordGenerator simplePasswordGenerator
    ) {
        this.korisnikDAO = korisnikDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        this.recAlgDAO = recAlgDAO;
        this.smestajDAO = smestajDAO;
        this.mailService = mailService;
        this.simplePasswordGenerator = simplePasswordGenerator;
    }

    // TODO staviti da je putanja /resetdata
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

    @PostMapping("passwordresetvialemail/{username}")
    public ResponseEntity<String> passwordResetViaEmail(@PathVariable("username") String username){
        Korisnik korisnik = korisnikDAO.getUserByUsername(username).orElse(null);
        if(korisnik == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        String email = korisnik.getEmail();
        String newPassword = simplePasswordGenerator.generatePassword();
        korisnik.setSifra(newPassword);
        korisnikDAO.update(korisnik,(int)korisnik.getIdkorisnik());
        mailService.sendEmail(email,"Promena lozinke","Nova lozinka: " + newPassword);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}