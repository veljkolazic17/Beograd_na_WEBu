package rs.psi.beogradnawebu.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.LoginDTO;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.services.KorisnikServis;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class IndexController {
    private KorisnikServis korisnikServis;
    private KorisnikDAO korisnikDAO;

    public IndexController(KorisnikServis korisnikServis, KorisnikDAO korisnikDAO){
        this.korisnikServis = korisnikServis;
        this.korisnikDAO = korisnikDAO;
    }

    @GetMapping("/")
    public String login(@AuthenticationPrincipal User korisnik, Model model) {
        if(korisnik != null) {
            String username = korisnik.getUsername();
            model.addAttribute("korime", username);
            // proveri da li je korisnik ili admin
            return "glavnaStranicaKorisnik";
        }
        else {
            RegistracijaDTO regDTO = new RegistracijaDTO();
            model.addAttribute("registracija", regDTO);
            model.addAttribute("animacijaLosaRegistracija", false);

            return "login";
        }
    }

    @PostMapping("/registracija")
    public String registracija(@ModelAttribute("registracija") @Valid RegistracijaDTO regDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("animacijaLosaRegistracija", true);
            return "login";
        }
        korisnikServis.registrujNovogKorisnika(regDTO);
        return "glavnaStranicaKorisnik";
    }

    @GetMapping("/uloguj")
    public String uloguj(Model model) {
        RegistracijaDTO regDTO = new RegistracijaDTO();
        model.addAttribute("registracija", regDTO);
        model.addAttribute("animacijaLosaRegistracija", false);
        return "login";
    }

}
