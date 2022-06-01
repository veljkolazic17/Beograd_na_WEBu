package rs.psi.beogradnawebu.annotations;

import rs.psi.beogradnawebu.validators.RegIsteSifreValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RegIsteSifreValidator.class)
@Documented
public @interface RegistracijaIsteSifre {
    String message() default "Šifre se ne poklapaju!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
