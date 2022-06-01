package rs.psi.beogradnawebu.annotations;


import rs.psi.beogradnawebu.validators.EmailPripadaValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailPripadaValidator.class)
@Documented
public @interface PripadaEmail {
    String message() default "Priloženi email ne pripada Vama!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}