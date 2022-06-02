/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.PromenaMailaDTO;
import rs.psi.beogradnawebu.dto.PromenaSifreDTO;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;
import rs.psi.beogradnawebu.services.KorisnikServis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * IndexController - klasa koja sluzi za upravljanje registracijom i loginom
 * @version 1.0
 */
@Controller
public class IndexController {
    private KorisnikServis korisnikServis;
    private KorisnikDAO korisnikDAO;

    private static final Logger log = LoggerFactory.getLogger(FilterController.class);

    /**
     * IndexController - konstruktor, prosledjuju mu se zavisnosti
     * @param korisnikServis
     * @param korisnikDAO
     */
    public IndexController(KorisnikServis korisnikServis, KorisnikDAO korisnikDAO){
        this.korisnikServis = korisnikServis;
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * login - metoda koja sluzi da uloguje korisnika, ili ga vrati na login ako nije uspesan login
     * @param korisnik
     * @param model
     * @param greska
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@AuthenticationPrincipal User korisnik, Model model, @RequestParam(name = "error", required = false) String greska) {
        if(korisnik != null && greska == null) {
            String username = korisnik.getUsername();
            model.addAttribute("korime", username);
            String email = korisnikDAO.getUserByUsername(korisnik.getUsername()).get().getEmail();
            model.addAttribute("email", email);

            PromenaMailaDTO promMailaDTO = new PromenaMailaDTO();
            model.addAttribute("promenaEmaila", promMailaDTO);
            PromenaSifreDTO promSifDTO = new PromenaSifreDTO();
            model.addAttribute("promenaSifre", promSifDTO);

            return "redirect:/pregledsmestaja/0";
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

    /**
     * registracija - metoda koja sluzi da registruje korisnika a zatim ga prosledi na glavnu stranicu,
     *                ili nazad na login ako je registracija neuspesna
     * @param request
     * @param regDTO
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/registracija")
    public String registracija(HttpServletRequest request, @ModelAttribute("registracija") @Valid RegistracijaDTO regDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("animacijaLosaRegistracija", true);
            model.addAttribute("greska", false);
            return "login";
        }
        korisnikServis.registrujNovogKorisnika(regDTO);
        try {
            request.login(regDTO.getKorime(), regDTO.getSifra());
        } catch (ServletException e) {
            log.info("Nije moguce ulogovati korisnika!");
            return "login";
        }
        return "redirect:/pregledsmestaja/0";
    }
}
