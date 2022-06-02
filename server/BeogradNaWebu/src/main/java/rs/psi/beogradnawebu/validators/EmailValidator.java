/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.ValidanEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailValidator - klasa koja proverava da li je email u validnom obliku
 * @version 1.0
 */
public class EmailValidator implements ConstraintValidator<ValidanEmail, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    /**
     * initialize - neophodna metoda za Validator, nepotrebna u ovom slucaju
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ValidanEmail constraintAnnotation) {
    }

    /**
     * isValid - metoda koja proverava da li je email u validnom obliku
     * @param email object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
