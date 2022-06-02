/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.annotations;

import rs.psi.beogradnawebu.validators.KorimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ValidnoKorime - interfejs koji sluzi da naznaci da polje za korisnicko
 *                 ime sadrzi korisnicko ime koje nije u upotrebi
 * @version 1.0
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = KorimeValidator.class)
@Documented
public @interface ValidnoKorime {
    String message() default "Korisničko ime već postoji!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
