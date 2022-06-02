/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.ValidnoKorime;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * KorimeValidator - klasa koja sluzi da proveri da li je korisnicko ime vec u upotrebi
 * @version 1.0
 */
public class KorimeValidator implements ConstraintValidator<ValidnoKorime, String> {
    private KorisnikDAO korisnikDAO;

    /**
     * KorimeValidator - konstruktor, prosledjivanje zavisnosti
     * @param korisnikDAO
     */
    public KorimeValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ValidnoKorime constraintAnnotation) {
    }

    /**
     * isValid - metoda koja se poziva da se proveri da li korisnik sa zadatim nadimkom vec postoji
     * @param korisnik object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String korisnik, ConstraintValidatorContext context){
        return !korisnikDAO.getUserByUsername(korisnik).isPresent();
    }
}
