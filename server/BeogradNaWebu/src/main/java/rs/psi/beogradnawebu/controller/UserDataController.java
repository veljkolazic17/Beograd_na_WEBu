package rs.psi.beogradnawebu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;

import javax.management.openmbean.OpenMBeanInfoSupport;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/userdata")
public class UserDataController {

    private final KorisnikDAO korisnikDAO;
    private final LajkSmestajaCDAO lajkSmestajaCDAO;
    private final RecAlgDAO recAlgDAO;

    public UserDataController(KorisnikDAO korisnikDAO, LajkSmestajaCDAO lajkSmestajaCDAO,RecAlgDAO recAlgDAO) {
        this.korisnikDAO = korisnikDAO;
        this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        this.recAlgDAO = recAlgDAO;
    }

    @PostMapping("resetdata")
    public String resetData(Principal principal) {
//        String username = principal.getName();
//        Korisnik korisnik = korisnikDAO.getUserByUsername(username).orElse(null);
//        if(korisnik != null){
//            int idkorisnik = (int)korisnik.getIdkorisnik();
//            List<LajkSmestaja> lajkovi = lajkSmestajaCDAO.getLikes((int) korisnik.getIdkorisnik()).orElse(null);
//            if (lajkovi != null) {
//                for (LajkSmestaja lajkSmestaja : lajkovi) {
//                    lajkSmestajaCDAO.delete(new int[]{idkorisnik, (int) lajkSmestaja.getIdsmestaj()});
//                }
//            }
//            recAlgDAO.delete(idkorisnik);
//        }

        return "glavnaStranicaKorisnik.html";
    }
}