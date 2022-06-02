/**
 * Marko Mirkovic 2019/0197
 */

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

/**
 * KorimeValidator - klasa koja sluzi da proveri da li se sifra i nadimak poklapaju
 * @version 1.0
 */
public class SifraPripadaValidator implements ConstraintValidator<PripadaSifra, String> {
    private Authentication korisnik;
    private KorisnikDAO korisnikDAO;

    /**
     * SifraPripadaValidator - konstruktor, prosledjivanje zavisnosti
     * @param korisnikDAO
     */
    public SifraPripadaValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(PripadaSifra constraintAnnotation) {
    }

    /**
     * isValid - metoda koja se poziva da se proveri da li se sifra i nadimak poklapaju
     * @param sifra object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String sifra, ConstraintValidatorContext context){
        this.korisnik = SecurityContextHolder.getContext().getAuthentication();
        return korisnikDAO.getUserByUsername(korisnik.getName()).get().getSifra().equals(sifra);
    }
}
