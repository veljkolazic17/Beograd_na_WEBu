package rs.psi.beogradnawebu.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.misc.FilterForm;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
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

    @PostMapping("/filter")
    public String filterSmestaj(@Valid @ModelAttribute("filterData") FilterForm filterData, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        List<Smestaj> smestajList = smestajDAO.searchByFilters(filterData);
        redirectAttributes.addFlashAttribute("smestajList",smestajList);
        return "redirect:/pregledsmestaja";
    }

    @GetMapping("/isliked/{username}/{idsmestaj}")
    public String isLiked(@PathParam("username") String username, @PathParam("idsmestaj") int idsmestaj,RedirectAttributes redirectAttributes){
        int idkorisnik = (int)korisnikDAO.getUserByUsername(username).get().getIdkorisnik();
        LajkSmestaja lajkSmestaja = lajkSmestajaCDAO.get(new int[]{idkorisnik,idsmestaj}).orElse(null);
        redirectAttributes.addFlashAttribute("isliked",lajkSmestaja != null);
        return "redirect:/pregledsmestaja";
    }


    @GetMapping
    public String listSmestaj(Principal principal, Model model){
        model.addAttribute("filterData",new FilterForm());
        if(principal.getName() == null)
            model.addAttribute("username",null);
        else{
            model.addAttribute("username",principal.getName());
        }
        return "glavnaStranicaGost";
    }
}
