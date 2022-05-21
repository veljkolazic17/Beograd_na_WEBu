package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.ValidnaSifra;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SifraValidator implements ConstraintValidator<ValidnaSifra, Object> {

    @Override
    public void initialize(ValidnaSifra constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistracijaDTO korisnik = (RegistracijaDTO) obj;
        return korisnik.getSifra().equals(korisnik.getPotvrdaSifre());
    }
}
