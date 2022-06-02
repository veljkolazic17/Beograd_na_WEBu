package rs.psi.beogradnawebu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.PromenaMailaDTO;
import rs.psi.beogradnawebu.dto.PromenaSifreDTO;
import rs.psi.beogradnawebu.model.Korisnik;

import javax.validation.Valid;

@Controller
public class InfoChangeController {
    private KorisnikDAO korisnikDAO;

    public InfoChangeController(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }
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

    @PostMapping("/promena/pretplata")
    public ResponseEntity<String> promenaPretplate(@AuthenticationPrincipal User user){
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).orElse(null);
        korisnik.setEpredlog((korisnik.getEpredlog() == 0)?1:0);
        korisnikDAO.update(korisnik, (int) korisnik.getIdkorisnik());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
