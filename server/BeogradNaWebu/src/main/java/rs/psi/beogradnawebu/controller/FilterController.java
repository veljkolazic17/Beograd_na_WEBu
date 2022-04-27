package rs.psi.beogradnawebu.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.psi.beogradnawebu.misc.FilterForm;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/pregledsmestaja")
public class FilterController {

    private ObjectMapper objectMapper;

    @PostMapping("/filter")
    public String filterSmestaj(@Valid @ModelAttribute("formData") FilterForm formData,
                                BindingResult bindingResult,
                                Model model){


        return null;
    }


    @GetMapping
    public String listSmestaj(Model model){


        return null;
    }

    
}
