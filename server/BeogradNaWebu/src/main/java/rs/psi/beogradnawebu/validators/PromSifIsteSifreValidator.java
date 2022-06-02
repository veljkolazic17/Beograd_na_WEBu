/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.PromSifIsteSifre;
import rs.psi.beogradnawebu.dto.PromenaSifreDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * PromSifIsteSifreValidator - klasa koja sluzi da proveri da li polja za novu sifru sadrze istu sifru
 * @version 1.0
 */
public class PromSifIsteSifreValidator implements ConstraintValidator<PromSifIsteSifre, Object> {

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(PromSifIsteSifre constraintAnnotation) {
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
        PromenaSifreDTO dto = (PromenaSifreDTO) obj;
        return dto.getNovaSifra().equals(dto.getPonovljenaNovaSifra());
    }
}
