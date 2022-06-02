/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FilterController - klasa za prikaz i pretragu smestaja
 * @version 1.0
 */
@Controller
@RequestMapping("/pregledsmestaja")
public class FilterController {

    private static final Logger log = LoggerFactory.getLogger(FilterController.class);
    private final SmestajDAO smestajDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final KorisnikDAO korisnikDAO;

    /**
     * Kreiranje nove instance
     * @param smestajDAO
     * @param lajkSmestajaCDAO
     * @param korisnikDAO
     */
    public FilterController(SmestajDAO smestajDAO,LajkSmestajaCDAO lajkSmestajaCDAO,KorisnikDAO korisnikDAO){
        this.smestajDAO = smestajDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * Metoda koja se koristi za pretragu smestaja po filterima
     * @param request
     * @param user
     * @param filterData
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/filter")
    public String filterSmestaj(HttpServletRequest request,@AuthenticationPrincipal User user,@Valid @ModelAttribute("filterData") FilterDTO filterData, RedirectAttributes redirectAttributes){
        HttpSession mySession = request.getSession();
        if(!filterData.isNull()){
            mySession.setAttribute("myfilter",filterData);
            redirectAttributes.addFlashAttribute("filterf",filterData);
            mySession.setAttribute("displayflag",1);
        }else if(mySession.getAttribute("myfilter") == null){
            mySession.setAttribute("displayflag",null);
        }
        else{
            redirectAttributes.addFlashAttribute("filterf",(FilterDTO)mySession.getAttribute("myfilter"));
            mySession.setAttribute("displayflag",1);
        }
        return "redirect:/pregledsmestaja/0";
    }

    /**
     * Metoda koja se korisi za prelistavanje pokupljenih smestaja
     * @param request
     * @param korisnik
     * @param nstranica
     * @param model
     * @return
     */
    @GetMapping("/{nstranica}")
    public String listSmestaj(HttpServletRequest request , @AuthenticationPrincipal User korisnik,@PathVariable() int nstranica, Model model){
        model.addAttribute("filterData",new FilterDTO());
        HttpSession mySession = request.getSession();
        model.addAttribute("pagen",nstranica);
        model.addAttribute("lastPage",false);
        if(korisnik!=null) {
            if(mySession.getAttribute("displayflag")==null) {
                List<Smestaj> smestajList = smestajDAO.getByOffset(nstranica,10);
                if(smestajList.size() == 0){
                    model.addAttribute("lastPage",true);
                }
                model.addAttribute("smestajList", smestajList);
            }
            else{
                boolean isRecommended = ((int)mySession.getAttribute("displayflag") == 2 && mySession.getAttribute("myrec")!=null);
                model.addAttribute("prikazPredlozenih", isRecommended);
                if(!isRecommended){
                    FilterDTO myfilter = (FilterDTO) mySession.getAttribute("myfilter");
                    model.addAttribute("filterf",myfilter);
                    List<Smestaj> smestajList = smestajDAO.searchByFilters(myfilter,nstranica);
                    if(smestajList.size() == 0){
                        model.addAttribute("lastPage",true);
                    }
                    model.addAttribute("smestajList", smestajList);
                }else{
                    List<Smestaj> smestajList = (List<Smestaj>) mySession.getAttribute("myrec");
                    if(smestajList.size() >= 10*(nstranica+1)){
                        smestajList = smestajList.subList(nstranica*10, (nstranica+1)*10);
                    }else if(smestajList.size() > 10*nstranica){
                        smestajList = smestajList.subList(nstranica*10,smestajList.size());
                    }
                    else{
                        smestajList = new ArrayList<>();
                        model.addAttribute("lastPage",true);
                    }
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
            else {
                model.addAttribute("listaKorisnika", korisnikDAO.list());
                return "glavnaStranicaAdmin";
            }
        }
        else {

            if(mySession.getAttribute("myfilter")==null) {
                List<Smestaj> smestajList = smestajDAO.getByOffset(nstranica,10);
                model.addAttribute("smestajList", smestajList.subList(0,10));
            }
            else{
                FilterDTO myfilter = (FilterDTO) mySession.getAttribute("myfilter");
                model.addAttribute("filterf",myfilter);
                List<Smestaj> smestajList = smestajDAO.searchByFilters((FilterDTO) mySession.getAttribute("myfilter"),nstranica);
                if(smestajList.size()==0){
                    model.addAttribute("lastPage",true);
                }
                model.addAttribute("smestajList", smestajList);
            }
            return "glavnaStranicaGost";
        }
    }
}
