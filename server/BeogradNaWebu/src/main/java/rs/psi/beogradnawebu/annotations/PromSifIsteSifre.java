package rs.psi.beogradnawebu.annotations;

import rs.psi.beogradnawebu.validators.PromSifIsteSifreValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PromSifIsteSifreValidator.class)
@Documented
public @interface PromSifIsteSifre {
    String message() default "Nove šifre se ne poklapaju!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
