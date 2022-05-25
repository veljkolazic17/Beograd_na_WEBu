package rs.psi.beogradnawebu.passwordGenerator;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

@Component
public class SimplePasswordGenerator {

    /*
    * Definisana pravila za generisanje lozinke
    * */
    public String generatePassword() {
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(5);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(5);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(5);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "@#$_";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(5);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        /*
        * Generisana duzina lozinke je 20 karaktera
        * */
        String password = passwordGenerator.generatePassword(30, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
        return password;
    }
}
