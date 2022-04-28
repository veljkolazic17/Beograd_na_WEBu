package rs.psi.beogradnawebu.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.misc.FilterForm;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/pregledsmestaja")
public class FilterController {

    private static final Logger log = LoggerFactory.getLogger(FilterController.class);
    private final SmestajDAO smestajDAO;
    public FilterController(SmestajDAO smestajDAO){
        this.smestajDAO = smestajDAO;
    }

    @PostMapping("/filter")
    public String filterSmestaj(@Valid @ModelAttribute("filterData") FilterForm filterData, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        List<Smestaj> smestajList = smestajDAO.searchByFilters(filterData);
        redirectAttributes.addFlashAttribute("smestajList",smestajList);
        return "redirect:/pregledsmestaja";
    }

    @GetMapping
    public String listSmestaj(Model model){
        model.addAttribute("filterData",new FilterForm());
        return "glavnaStranicaGost";
    }
}
