package rs.psi.beogradnawebu.annotations;

import rs.psi.beogradnawebu.validators.EmailJedinstvenValidator;
import rs.psi.beogradnawebu.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailJedinstvenValidator.class)
@Documented
public @interface JedinstvenEmail {
    String message() default "Email je već u upotrebi!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}