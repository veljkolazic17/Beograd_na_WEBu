package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.PromSifIsteSifre;
import rs.psi.beogradnawebu.dto.PromenaSifreDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PromSifIsteSifreValidator implements ConstraintValidator<PromSifIsteSifre, Object> {

    @Override
    public void initialize(PromSifIsteSifre constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        PromenaSifreDTO dto = (PromenaSifreDTO) obj;
        return dto.getNovaSifra().equals(dto.getPonovljenaNovaSifra());
    }
}