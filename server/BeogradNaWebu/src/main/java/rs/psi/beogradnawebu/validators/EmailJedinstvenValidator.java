package rs.psi.beogradnawebu.validators;

import rs.psi.beogradnawebu.annotations.JedinstvenEmail;
import rs.psi.beogradnawebu.dao.KorisnikDAO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailJedinstvenValidator implements ConstraintValidator<JedinstvenEmail, String> {

    private KorisnikDAO korisnikDAO;

    public EmailJedinstvenValidator(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    @Override
    public void initialize(JedinstvenEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateEmail(email));
    }
    private boolean validateEmail(String email) {
        return !korisnikDAO.getUserByEmail(email).isPresent();
    }
}