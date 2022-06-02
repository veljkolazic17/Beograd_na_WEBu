/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.RegistracijaIsteSifre;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * KorimeValidator - klasa koja sluzi da proveri da li polja za novu sifru sadrze istu sifru
 * @version 1.0
 */
public class RegIsteSifreValidator implements ConstraintValidator<RegistracijaIsteSifre, Object> {

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(RegistracijaIsteSifre constraintAnnotation) {
    }

    /**
     * isValid - metoda koja se poziva da se proveri da li polja za novu sifru sadrze istu sifru
     * @param obj object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistracijaDTO korisnik = (RegistracijaDTO) obj;
        boolean val = korisnik.getSifra().equals(korisnik.getPotvrdaSifre());
        if(!val) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode( "potvrdaSifre" ).addConstraintViolation();
        }
        return val;
    }
}
