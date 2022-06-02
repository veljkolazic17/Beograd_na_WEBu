package rs.psi.beogradnawebu.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import rs.psi.beogradnawebu.annotations.PripadaEmail;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailPripadaValidator implements ConstraintValidator<PripadaEmail, String> {
    private Authentication korisnik;
    private KorisnikDAO korisnikDAO;

    public EmailPripadaValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
        this.korisnik = SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public void initialize(PripadaEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        this.korisnik = SecurityContextHolder.getContext().getAuthentication();
        return validateEmail(email);
    }
    private boolean validateEmail(String email) {
        Optional<Korisnik> temp = korisnikDAO.getUserByEmail(email);
        if(temp.isEmpty()) return false;
        else if(temp.get().getKorisnickoime().equals(korisnik.getName())) return true;
        else return false;
    }
}
