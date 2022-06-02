/**
 * Marko Mirkovic 2019/0197
 */

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

/**
 * EmailPripadaValidator - klasa koja sluzi da proveri da li polje emaila sadrzi email koji pripada korisniku
 * @version 1.0
 */
public class EmailPripadaValidator implements ConstraintValidator<PripadaEmail, String> {
    private Authentication korisnik;
    private KorisnikDAO korisnikDAO;

    /**
     * EmailPripadaValidator - konstruktor, prosledjivanje zavisnosti
     * @param korisnikDAO
     */
    public EmailPripadaValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(PripadaEmail constraintAnnotation) {
    }

    /**
     * isValid - metoda koja se poziva da se proveri da li email pripada nekom korisniku
     * @param email object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        this.korisnik = SecurityContextHolder.getContext().getAuthentication();
        Optional<Korisnik> temp = korisnikDAO.getUserByEmail(email);
        if(temp.isEmpty()) return false;
        else if(temp.get().getKorisnickoime().equals(korisnik.getName())) return true;
        else return false;
    }
}
