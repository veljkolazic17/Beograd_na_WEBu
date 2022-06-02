/**
 * Marko Mirkovic 2019/0197
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.services.MailService;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.services.SimplePasswordGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@RestController
@RequestMapping("/userdata")
public class UserDataController {

    private final KorisnikDAO korisnikDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final RecAlgDAO recAlgDAO;
    private final SmestajDAO smestajDAO;
    private final MailService mailService;
    private final SimplePasswordGenerator simplePasswordGenerator;

    /**
     * Kreiranje instance
     * @param korisnikDAO
     * @param lajkSmestajaCDAO
     * @param recAlgDAO
     * @param smestajDAO
     * @param mailService
     * @param simplePasswordGenerator
     */
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

    /**
     * Metoda kojom se brise istorija predlozenoih stanova
     * @param request
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<String> resetData(HttpServletRequest request, @AuthenticationPrincipal User user) {
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).orElse(null);
        if(korisnik != null){
            HttpSession mySession = request.getSession();
            mySession.setAttribute("myrec",null);
            int idkorisnik = (int)korisnik.getIdkorisnik();
            smestajDAO.decLikes((int) korisnik.getIdkorisnik());
            lajkSmestajaCDAO.deleteLikes((int) korisnik.getIdkorisnik());
            recAlgDAO.delete(idkorisnik);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Metoda kojom se nova sifra salje na email korisnika
     * @param username
     * @return
     */
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

    @PostMapping("obrisiKorisnika/{korime}")
    public ResponseEntity<String> obrisiKorisnika(@AuthenticationPrincipal User user, @PathVariable("korime") String korime){
        Korisnik korisnik = korisnikDAO.getUserByUsername(korime).orElse(null);
        if(korisnik == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        if(korisnik.getKorisnickoime().equals(user.getUsername()))
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

        Collection<GrantedAuthority> roles = user.getAuthorities();
        for(GrantedAuthority role: roles) {
            System.out.println(role.getAuthority());
            if(role.getAuthority().equals("ROLE_ADMIN")) {
                korisnikDAO.delete((int)korisnik.getIdkorisnik());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}