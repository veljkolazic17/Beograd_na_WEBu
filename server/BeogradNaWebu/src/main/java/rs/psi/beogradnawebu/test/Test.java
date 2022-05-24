package rs.psi.beogradnawebu.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.MailService.MailService;

@RestController
@RequestMapping("/test")
public class Test {


    private final KorisnikDAO korisnikDAO;
    private final MailService mailService;

    public Test(KorisnikDAO korisnikDAO,MailService mailService) {
        this.korisnikDAO = korisnikDAO;
        this.mailService = mailService;
    }

    @GetMapping("/test1")
    public String test1(){
        Korisnik korisnik = korisnikDAO.getUserByUsername("maga").orElse(null);
        System.out.println(korisnik.getEmail() + " " + korisnik.getIdkorisnik());
        return "test";
    }

    @GetMapping("/send")
    public void send_email(){
        mailService.sendEmail("veljkolazic117@gmail.com","Provera email servisa","EmailServisRadi");
    }

}
