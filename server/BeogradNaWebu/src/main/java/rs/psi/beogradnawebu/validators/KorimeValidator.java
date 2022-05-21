package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.ValidnoKorime;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class KorimeValidator implements ConstraintValidator<ValidnoKorime, String> {
    private KorisnikDAO korisnikDAO;

    public KorimeValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    @Override
    public void initialize(ValidnoKorime constraintAnnotation) {
    }
    @Override
    public boolean isValid(String korisnik, ConstraintValidatorContext context){
        return !korisnikDAO.getUserByUsername(korisnik).isPresent();
    }
}