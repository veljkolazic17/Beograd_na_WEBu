package rs.psi.beogradnawebu.validators;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import rs.psi.beogradnawebu.annotations.PripadaEmail;
import rs.psi.beogradnawebu.annotations.PripadaSifra;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class SifraPripadaValidator implements ConstraintValidator<PripadaSifra, String> {
    private Authentication korisnik;
    private KorisnikDAO korisnikDAO;

    public SifraPripadaValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
        this.korisnik = SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public void initialize(PripadaSifra constraintAnnotation) {
    }

    @Override
    public boolean isValid(String sifra, ConstraintValidatorContext context){
        this.korisnik = SecurityContextHolder.getContext().getAuthentication();
        return validateSifra(sifra);
    }
    private boolean validateSifra(String sifra) {
        System.out.println(korisnikDAO.getUserByUsername(korisnik.getName()).get().getKorisnickoime());
        return korisnikDAO.getUserByUsername(korisnik.getName()).get().getSifra().equals(sifra);
    }
}