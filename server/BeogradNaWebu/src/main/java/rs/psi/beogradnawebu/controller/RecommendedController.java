package rs.psi.beogradnawebu.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pregledpredlozenihsmestaja")
public class RecommendedController {

    private final SmestajDAO smestajDAO;
    private final KorisnikDAO korisnikDAO;
    private final MMLVRecommenderImpl mmlvRecommender;
    private final RecAlgDAO recAlgDAO;

    public RecommendedController(SmestajDAO smestajDAO, KorisnikDAO korisnikDAO,MMLVRecommenderImpl mmlvRecommender,RecAlgDAO recAlgDAO){
        this.smestajDAO = smestajDAO;
        this.korisnikDAO = korisnikDAO;
        this.mmlvRecommender = mmlvRecommender;
        this.recAlgDAO = recAlgDAO;
    }


    @GetMapping
    public String listSmestaj(@AuthenticationPrincipal User user, RedirectAttributes redirectAttributes){
        Korisnik korisnik = korisnikDAO.getUserByUsername(user.getUsername()).orElse(null);
        if(korisnik == null)  return "redirect:/pregledsmestaja";
        List<Smestaj> smestajList = recommendAcc(korisnik);
        redirectAttributes.addFlashAttribute("smestajList",smestajList);
        return "redirect:/pregledsmestaja";
    }

    public List<Smestaj> recommendAcc(Korisnik k){
        List<Smestaj> result = null;
        SmestajDAO.AvgData avgAcc = smestajDAO.getAvgAcc(k.getIdkorisnik());
        Recalgdata recalgdata = recAlgDAO.get((int)k.getIdkorisnik()).orElse(null);
        try {
            int i = 0;
            result = new ArrayList<>();
            while(true){
                List<Smestaj> smestajList = smestajDAO.getByOffset(i,100);
                if(smestajList.isEmpty()){
                    break;
                }
                for(Smestaj s : smestajList){
                   if(mmlvRecommender.recommend(recalgdata,avgAcc,s) || recalgdata == null) result.add(s);

                    if(result.size() == 10) break;
                }
                if(result.size() == 10) break;
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
