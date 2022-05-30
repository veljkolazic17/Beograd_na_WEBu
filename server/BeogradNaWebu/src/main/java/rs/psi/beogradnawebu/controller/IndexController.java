package rs.psi.beogradnawebu.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.LoginDTO;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.services.KorisnikServis;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
public class IndexController {
    private KorisnikServis korisnikServis;
    private KorisnikDAO korisnikDAO;

    public IndexController(KorisnikServis korisnikServis, KorisnikDAO korisnikDAO){
        this.korisnikServis = korisnikServis;
        this.korisnikDAO = korisnikDAO;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@AuthenticationPrincipal User korisnik, Model model, @RequestParam(name = "error", required = false) String greska) {
        if(korisnik != null && greska == null) {
            String username = korisnik.getUsername();
            model.addAttribute("korime", username);
            String email = korisnikDAO.getUserByUsername(korisnik.getUsername()).get().getEmail();
            model.addAttribute("email", email);

            return "redirect:/pregledsmestaja";
        }
        else {
            RegistracijaDTO regDTO = new RegistracijaDTO();
            model.addAttribute("registracija", regDTO);
            model.addAttribute("animacijaLosaRegistracija", false);

            if(greska != null) {
                model.addAttribute("greska", true);
            } else {
                model.addAttribute("greska", false);
            }

            return "login";
        }
    }

    @PostMapping("/registracija")
    public String registracija(@ModelAttribute("registracija") @Valid RegistracijaDTO regDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("animacijaLosaRegistracija", true);
            model.addAttribute("greska", false);
            return "login";
        }
        korisnikServis.registrujNovogKorisnika(regDTO);
        return "redirect:/pregledsmestaja";
    }

}
