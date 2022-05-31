package rs.psi.beogradnawebu.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.dto.FilterDTO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.ArrayList;
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
        List<Smestaj> smestajList = smestajDAO.searchByFilters(filterData,nstranica);
        redirectAttributes.addFlashAttribute("smestajList",smestajList);
        return "redirect:/pregledsmestaja";
    }

    @GetMapping
    public String listSmestaj(@AuthenticationPrincipal User korisnik,Model model){
        model.addAttribute("filterData", new FilterDTO());
        if(korisnik!=null) {
            Korisnik k = korisnikDAO.getUserByUsername(korisnik.getUsername()).get();
            model.addAttribute("user", k);
            if(k.getUloga() == 0)
                return "glavnaStranicaKorisnik";
            else
                return "glavnaStranicaAdmin";
        }
        else
            return "glavnaStranicaGost";
    }
}
