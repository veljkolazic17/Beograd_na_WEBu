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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/filter")
    public String filterSmestaj(HttpServletRequest request,@AuthenticationPrincipal User user,@Valid @ModelAttribute("filterData") FilterDTO filterData, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("filterf",filterData);
        List<Smestaj> smestajList;
        if(filterData!= null) {
            smestajList = smestajDAO.searchByFilters(filterData);
            HttpSession mySession = request.getSession();
            mySession.setAttribute("sviSmestaji",smestajList);
            redirectAttributes.addFlashAttribute("smestajList",smestajList);
        }
        return "redirect:/pregledsmestaja/0";
    }

    @GetMapping("/{nstranica}")
    public String listSmestaj(HttpServletRequest request , @AuthenticationPrincipal User korisnik,@PathVariable("nstranica")Integer nstranica, Model model){
        model.addAttribute("filterData",new FilterDTO());
        HttpSession mySession = request.getSession();
        if(korisnik!=null) {
            if(mySession.getAttribute("sviSmestaji")==null) {
                List<Smestaj> smestajList = smestajDAO.list();
                mySession.setAttribute("sviSmestaji",smestajList);
                model.addAttribute("smestajList", smestajList.subList(0,10));
            }
            else{
                List<Smestaj> smestajList = (List<Smestaj>) mySession.getAttribute("sviSmestaji");
                if(smestajList.size()>=10){
                    if(nstranica*10 < smestajList.size()) {
                        model.addAttribute("smestajList", smestajList.subList(nstranica * 10, (nstranica + 1) * 10));
                    }
                }
                else {
                    model.addAttribute("smestajList", smestajList);

                }
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

            if(mySession.getAttribute("sviSmestaji")==null) {
                List<Smestaj> smestajList = smestajDAO.list();
                mySession.setAttribute("sviSmestaji",smestajList);
                model.addAttribute("smestajList", smestajList.subList(0,10));
            }
            else {
                List<Smestaj> smestajList = (List<Smestaj>) mySession.getAttribute("sviSmestaji");
                if(smestajList.size()>=10) {
                    if(nstranica*10 < smestajList.size()) {
                        model.addAttribute("smestajList", smestajList.subList(nstranica * 10, (nstranica + 1) * 10));
                    }
                }
                else {
                    model.addAttribute("smestajList", smestajList);

                }
            }
            return "glavnaStranicaGost";
        }
    }
}
