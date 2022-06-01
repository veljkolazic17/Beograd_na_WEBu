package rs.psi.beogradnawebu.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String promenaEmaila(@AuthenticationPrincipal User user, @Valid @ModelAttribute("promenaMaila") PromenaMailaDTO mailovi, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:/logout";
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).get();
        korisnik.setEmail(mailovi.getNoviEmail());
        korisnikDAO.update(korisnik, (int) korisnik.getIdkorisnik());
        return "redirect:/pregledsmestaja/0";
    }

    @PostMapping("/promena/sifra")
    public String promenaSifre(@AuthenticationPrincipal User user, @Valid @ModelAttribute("promenaSifre") PromenaSifreDTO sifre, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:/logout";
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).get();
        korisnik.setSifra(sifre.getNovaSifra());
        korisnikDAO.update(korisnik, (int) korisnik.getIdkorisnik());
        return "redirect:/logout";
    }
}
