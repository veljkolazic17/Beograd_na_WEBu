package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.RegistracijaIsteSifre;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegIsteSifreValidator implements ConstraintValidator<RegistracijaIsteSifre, Object> {

    @Override
    public void initialize(RegistracijaIsteSifre constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistracijaDTO korisnik = (RegistracijaDTO) obj;
        boolean val = korisnik.getSifra().equals(korisnik.getPotvrdaSifre());
        if(!val) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode( "potvrdaSifre" ).addConstraintViolation();
        }
        return val;
    }
}
