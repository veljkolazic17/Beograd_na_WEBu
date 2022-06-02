/**
 * Jelena Lucic 2019/0268
 */
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

/**
 * ComController - kontroler za rad sa komentarima(dodavanje, brisanje, lajk, dislajk i prikazivanje)
 * @version 1.0
 */
@RestController
public class ComController {
    private static final Logger log = LoggerFactory.getLogger(ComController.class);
    private final SmestajDAO smestajDAO;
    private final KorisnikDAO korisnikDAO;
    private final KomentarDAO komentarDAO;
    private final LajkKomentaraCDAO lajkKomentaraCDAO;

    /**
     * Kreiranje nove instance
     * @param smestajDAO
     * @param korisnikDAO
     * @param komentarDAO
     * @param lajkKomentaraCDAO
     */
    public ComController(SmestajDAO smestajDAO, KorisnikDAO korisnikDAO, KomentarDAO komentarDAO, LajkKomentaraCDAO lajkKomentaraCDAO) {
        this.smestajDAO = smestajDAO;
        this.korisnikDAO = korisnikDAO;
        this.komentarDAO = komentarDAO;
        this.lajkKomentaraCDAO = lajkKomentaraCDAO;
    }

    /**
     * Metoda za dodavanje novog komentara u bazu
     * @param korisnik
     * @param idSmestaj
     * @param TextKomentara
     * @return
     */
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

    /**
     * Metoda za dohvatanje komentara za odredjeni smestaj
     * @param idsmestaj
     * @return
     */
    @GetMapping("/sviKomentari/{idsmestaj}")
    ResponseEntity<List<Komentar>> allKomentar(@PathVariable Integer idsmestaj) {
        List<Komentar> listKomentar = komentarDAO.allKomentar(idsmestaj);
        return new ResponseEntity<>(listKomentar, HttpStatus.OK);
    }

    /**
     * Metoda za dohvatanje maksimalnog ID-a komentara iz baze
     * @return
     */
    @GetMapping("/maxID")
    ResponseEntity<Integer> maxID() {
        Integer maxID = komentarDAO.maxID();
        return new ResponseEntity<>(maxID, HttpStatus.OK);
    }

    /**
     * Metoda za azuriranje baze prilikom lajkovanja odredjenog komentara
     * @param korisnik
     * @param idkomentar
     * @return
     */
    @PostMapping("/komentarLike/{idkomentar}")
    public ResponseEntity<String> likeKomentar(@AuthenticationPrincipal User korisnik, @PathVariable Integer idkomentar) {

        System.out.println("lajk");

        if(korisnik == null) return null;

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

    /**
     * Metoda za azuriranje baze prilikom dislajkovanja odredjenog komentara
     * @param korisnik
     * @param idkomentar
     * @return
     */
    @PostMapping("/unlikeKomentar/{idkomentar}")
    public ResponseEntity<String> unlikeKomentar(@AuthenticationPrincipal User korisnik, @PathVariable Integer idkomentar){

        System.out.println("dislajk");

        if(korisnik == null) return null;

        Korisnik k = korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);
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

    /**
     * Metoda za proveru da li je odredjeni komentar lajkovan
     * @param username
     * @param idkomentar
     * @return
     */
    @GetMapping(value = "/islikedKomentar/{username}/{idkomentar}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("username") String username, @PathVariable("idkomentar") Integer idkomentar){

        Korisnik k = korisnikDAO.getUserByUsername(username).orElse(null);
        if(k == null){
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
        int idkorisnik = (int)k.getIdkorisnik();
        LajkKomentara lajkKomentara = lajkKomentaraCDAO.get(new int[]{idkomentar, (int)k.getIdkorisnik()}).orElse(null);
        return new ResponseEntity<Boolean>(lajkKomentara != null, HttpStatus.OK);
    }

    /**
     * Metoda za azuriranje baze prilikom brisanja komentara
     * @param korisnik
     * @param idkomentar
     * @return
     */
    @PostMapping("obrisiKomentar/{idkomentar}")
    public ResponseEntity<String> deleteKomentar(@AuthenticationPrincipal User korisnik, @PathVariable("idkomentar") Integer idkomentar) {

        if(korisnik == null) return null;

        Korisnik k = korisnikDAO.getUserByUsername(korisnik.getUsername()).orElse(null);
        Komentar kom = komentarDAO.get(idkomentar).orElse(null);

        if(k.getUloga() == 1 || kom.getIdkorisnik() == k.getIdkorisnik()) {
            try {
                komentarDAO.delete(idkomentar);
            } catch (Exception e) {
                log.info("Error");
                return ResponseEntity.status(500).build();
            }
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }
}