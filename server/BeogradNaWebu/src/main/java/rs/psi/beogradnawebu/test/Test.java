package rs.psi.beogradnawebu.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;

@Controller
@RequestMapping("/test")
public class Test {


    private final KorisnikDAO korisnikDAO;

    public Test(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    @GetMapping
    public String test1(){
        Korisnik korisnik = korisnikDAO.getUserByUsername("maga").orElse(null);
        System.out.println(korisnik.getEmail() + " " + korisnik.getIdkorisnik());
        return "test";
    }



}
