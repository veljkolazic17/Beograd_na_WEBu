/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.JedinstvenEmail;
import rs.psi.beogradnawebu.dao.KorisnikDAO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * EmailJedinstvenValidator - klasa koja sluzi da proveri da li polje pri registraciji ili promeni emaila
 *                            sadrzi email koji je vec u upotrebi
 * @version 1.0
 */
public class EmailJedinstvenValidator implements ConstraintValidator<JedinstvenEmail, String> {

    private KorisnikDAO korisnikDAO;

    /**
     * EmailJedinstvenValidator - konstruktor, prosledjivanje zavisnosti
     * @param korisnikDAO
     */
    public EmailJedinstvenValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(JedinstvenEmail constraintAnnotation) {
    }

    /**
     * isValid - metoda koja se poziva da se proveri da li je ispravan email
     * @param email object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return !korisnikDAO.getUserByEmail(email).isPresent();
    }
}
