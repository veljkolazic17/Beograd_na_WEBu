package rs.psi.beogradnawebu.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.mailService.MailService;
import rs.psi.beogradnawebu.passwordGenerator.SimplePasswordGenerator;


@RestController
@RequestMapping("/test")
public class Test {


    private final KorisnikDAO korisnikDAO;
    private final MailService mailService;
    private final SimplePasswordGenerator simplePasswordGenerator;

    public Test(KorisnikDAO korisnikDAO,MailService mailService,SimplePasswordGenerator simplePasswordGenerator) {
        this.korisnikDAO = korisnikDAO;
        this.mailService = mailService;
        this.simplePasswordGenerator = simplePasswordGenerator;
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

    @GetMapping("/password")
    public String passowrd(){
        return simplePasswordGenerator.generatePassword();
    }

}
