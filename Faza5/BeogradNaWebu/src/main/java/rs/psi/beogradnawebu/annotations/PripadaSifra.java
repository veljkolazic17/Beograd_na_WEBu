/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.annotations;


import rs.psi.beogradnawebu.validators.SifraPripadaValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * PripadaSifra - interfejs koji sluzi da naznaci da polje sifre sadrzi
 *                sifru korisnika
 * @version 1.0
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SifraPripadaValidator.class)
@Documented
public @interface PripadaSifra {
    String message() default "Priložena šifra nije validna!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
