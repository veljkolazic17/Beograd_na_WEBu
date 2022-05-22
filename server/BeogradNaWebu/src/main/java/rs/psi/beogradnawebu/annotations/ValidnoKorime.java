package rs.psi.beogradnawebu.annotations;

import rs.psi.beogradnawebu.validators.EmailValidator;
import rs.psi.beogradnawebu.validators.KorimeValidator;
import rs.psi.beogradnawebu.validators.SifraValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = KorimeValidator.class)
@Documented
public @interface ValidnoKorime {
    String message() default "Korisničko ime već postoji!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}