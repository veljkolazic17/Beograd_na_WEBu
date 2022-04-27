package rs.psi.beogradnawebu.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rs.psi.beogradnawebu.model.Smestaj;
import java.util.List;


@Controller
public class FilterController {

    private ObjectMapper objectMapper;
    FilterController(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }


    


    
}
