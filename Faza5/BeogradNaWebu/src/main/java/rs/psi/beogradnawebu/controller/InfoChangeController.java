/**
 * Marko Mirkovic 2019/0197
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 * */
package rs.psi.beogradnawebu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.annotations.PripadaEmail;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.PromenaMailaDTO;
import rs.psi.beogradnawebu.dto.PromenaSifreDTO;
import rs.psi.beogradnawebu.model.Korisnik;

import javax.validation.Valid;

/**
 * InfoChangeController - endpoint koji sluzi za promenu osnovnih informacija korisnika
 * @version 1.0
 */
@Controller
public class InfoChangeController {

    private KorisnikDAO korisnikDAO;

    /**
     * InfoChangeController - konstruktor, prosledjuju mu se zavisnosti
     * @param korisnikDAO
     */
    public InfoChangeController(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * promenaEmaila - metoda koja sluzi za promenu emaila u panelu za korisnike i admine
     * @param user
     * @param mailovi
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/promena/email")
    public String promenaEmaila(@AuthenticationPrincipal User user,
                                @Valid @ModelAttribute("promenaMaila") PromenaMailaDTO mailovi,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("greskePriPromeniEmailaList", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("uspesnaPromenaEmaila", false);
            return "redirect:/pregledsmestaja/0";
        }
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).get();
        korisnik.setEmail(mailovi.getNoviEmail());
        korisnikDAO.update(korisnik, (int) korisnik.getIdkorisnik());

        redirectAttributes.addFlashAttribute("uspesnaPromenaEmaila", true);
        return "redirect:/pregledsmestaja/0";
    }

    /**
     * promenaSifre - metoda koja sluzi za promenu sifre u panelu za korisnike i admine
     * @param user
     * @param sifre
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/promena/sifra")
    public String promenaSifre(@AuthenticationPrincipal User user,
                               @Valid @ModelAttribute("promenaSifre") PromenaSifreDTO sifre,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("greskePriPromeniSifreList", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("uspesnaPromenaSifre", false);
            return "redirect:/pregledsmestaja/0";
        }

        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).get();
        korisnik.setSifra(sifre.getNovaSifra());
        korisnikDAO.update(korisnik, (int) korisnik.getIdkorisnik());

        return "redirect:/logout";
    }

    /**
     * Funkcija kojom se menja da stanje pretplate na mail servis
     * @param user
     * @return
     */
    @PostMapping("/promena/pretplata")
    public ResponseEntity<String> promenaPretplate(@AuthenticationPrincipal User user){
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).orElse(null);
        korisnik.setEpredlog((korisnik.getEpredlog() == 0)?1:0);
        korisnikDAO.update(korisnik, (int) korisnik.getIdkorisnik());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
