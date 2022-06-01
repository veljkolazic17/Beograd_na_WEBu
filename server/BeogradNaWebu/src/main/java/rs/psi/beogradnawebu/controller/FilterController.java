package rs.psi.beogradnawebu.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.dto.FilterDTO;
import rs.psi.beogradnawebu.dto.PromenaMailaDTO;
import rs.psi.beogradnawebu.dto.PromenaSifreDTO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/pregledsmestaja")
public class FilterController {

    private static final Logger log = LoggerFactory.getLogger(FilterController.class);
    private final SmestajDAO smestajDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final KorisnikDAO korisnikDAO;
    public FilterController(SmestajDAO smestajDAO,LajkSmestajaCDAO lajkSmestajaCDAO,KorisnikDAO korisnikDAO){
        this.smestajDAO = smestajDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        this.korisnikDAO = korisnikDAO;
    }

    @PostMapping("/filter/{nstranica}")
    public String filterSmestaj(@AuthenticationPrincipal User user,@PathVariable("nstranica") int nstranica,@Valid @ModelAttribute("filterData") FilterDTO filterData, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("filterf",filterData);
        List<Smestaj> smestajList;
        if(filterData!= null) {
            smestajList = smestajDAO.searchByFilters(filterData, nstranica);
        }
        else {
            smestajList = smestajDAO.getByOffset(nstranica,10);
        }
        redirectAttributes.addFlashAttribute("smestajList",smestajList);
        return "redirect:/pregledsmestaja";
    }

    @GetMapping
    public String listSmestaj(@AuthenticationPrincipal User korisnik,Model model){
        model.addAttribute("filterData",new FilterDTO());
        if(korisnik!=null) {
            if(!model.containsAttribute("smestajList")) {
                List<Smestaj> smestajList = smestajDAO.getByOffset(0, 10);
                model.addAttribute("smestajList", smestajList);
            }
            Korisnik k = korisnikDAO.getUserByUsername(korisnik.getUsername()).get();
            model.addAttribute("user", k);

            PromenaMailaDTO promMailaDTO = new PromenaMailaDTO();
            model.addAttribute("promenaEmaila", promMailaDTO);
            PromenaSifreDTO promSifDTO = new PromenaSifreDTO();
            model.addAttribute("promenaSifre", promSifDTO);

            if(k.getUloga() == 0)
                return "glavnaStranicaKorisnik";
            else
                return "glavnaStranicaAdmin";
        }
        else {
            List<Smestaj> smestajList = smestajDAO.getByOffset(0, 10);
            model.addAttribute("smestajList", smestajList);
            return "glavnaStranicaGost";
        }
    }
}
