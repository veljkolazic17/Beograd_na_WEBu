package rs.psi.beogradnawebu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.psi.beogradnawebu.dao.*;
import rs.psi.beogradnawebu.model.*;

import java.util.List;

@RestController
public class ComController {
    private static final Logger log = LoggerFactory.getLogger(ComController.class);

    private final SmestajDAO smestajDAO;
    private final KorisnikDAO korisnikDAO;
    private final KomentarDAO komentarDAO;

    private final LajkKomentaraCDAO lajkKomentaraCDAO;

    public ComController(SmestajDAO smestajDAO, KorisnikDAO korisnikDAO, KomentarDAO komentarDAO, LajkKomentaraCDAO lajkKomentaraCDAO) {
        this.smestajDAO = smestajDAO;
        this.korisnikDAO = korisnikDAO;
        this.komentarDAO = komentarDAO;
        this.lajkKomentaraCDAO = lajkKomentaraCDAO;
    }

    @PostMapping("/noviKomentar/{idSmestaj}/{TextKomentara}")
    public ResponseEntity<String> addKomentar(@AuthenticationPrincipal User korisnik, @PathVariable Integer idSmestaj, @PathVariable String TextKomentara) {

        System.out.println("komentar");

        if (korisnik == null) return null;
        Korisnik k = korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);

        Komentar komentar = new Komentar();
        komentar.setIdkorisnik(k.getIdkorisnik());
        komentar.setIdsmestaj(idSmestaj);
        komentar.setTekstKomentara(TextKomentara);

        try {
            komentarDAO.create(komentar);
        } catch (Exception e) {
            log.info("Error!");
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("sviKomentari/{idsmestaj}")
    ResponseEntity<List<Komentar>> allKomentar(@PathVariable Integer idsmestaj) {
        List<Komentar> listKomentar = komentarDAO.allKomentar(idsmestaj);
        return new ResponseEntity<>(listKomentar, HttpStatus.OK);
    }

    @GetMapping("maxID")
    ResponseEntity<Integer> maxID() {
        Integer maxID = komentarDAO.maxID();
        return new ResponseEntity<>(maxID, HttpStatus.OK);
    }

    @PostMapping("komentarLike/{idkomentar}")
    public ResponseEntity<String> likeKomentar(@AuthenticationPrincipal User korisnik, @PathVariable Integer idkomentar) {

        System.out.println("lajk");

        Korisnik k = korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);
        Komentar kom = komentarDAO.get(idkomentar).orElse(null);

        if(kom != null && k != null) {
            LajkKomentara lajkKomentara = new LajkKomentara();
            lajkKomentara.setIdkorisnik(k.getIdkorisnik());
            lajkKomentara.setIdkomentar(idkomentar);
            kom.setBroj_lajkova(kom.getBroj_lajkova() + 1);
            lajkKomentaraCDAO.create(lajkKomentara);
            komentarDAO.update(kom, idkomentar);
            return ResponseEntity.status(200).build();
        }
        else {
            log.info("Error!");
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("unlikeKomentar/{idkomentar}")
    public ResponseEntity<String> unlikeKomentar(@AuthenticationPrincipal User korisnik, @PathVariable Integer idkomentar){

        System.out.println("dislajk");

        Korisnik k =korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);
        Komentar kom = komentarDAO.get(idkomentar).orElse(null);

        if(kom != null && k != null) {
            kom.setBroj_lajkova(kom.getBroj_lajkova() - 1);
            komentarDAO.update(kom, idkomentar);
            lajkKomentaraCDAO.delete(new int[]{idkomentar, (int)k.getIdkorisnik()});
        }
        else {
            log.info("Error!");
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "islikedKomentar/{username}/{idkomentar}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("username") String username, @PathVariable("idkomentar") Integer idkomentar){

        Korisnik k = korisnikDAO.getUserByUsername(username).orElse(null);
        if(k == null){
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
        int idkorisnik = (int)k.getIdkorisnik();
        LajkKomentara lajkKomentara = lajkKomentaraCDAO.get(new int[]{idkomentar, (int)k.getIdkorisnik()}).orElse(null);
        return new ResponseEntity<Boolean>(lajkKomentara != null, HttpStatus.OK);
    }
}