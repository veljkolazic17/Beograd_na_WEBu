/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.annotations;

import rs.psi.beogradnawebu.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ValidanEmail - interfejs koji sluzi da naznaci da polje za email
 *                sadrzi ispravno formiran email
 * @version 1.0
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidanEmail {
    String message() default "Email nije u validnom formatu!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
