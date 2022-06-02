/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.services;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

/**
 * SimplePasswordGenerator - klasa koja se koristi prilikom slanja novog mejla korisniku u slucaju da je zaboravio sifru.
 * Kreira nasumicnu sifru od 20 karaktera
 * @version 1.0
 */
@Component
public class SimplePasswordGenerator {

    /**
     * Metoda koja generise sifru
     * @return
     */
    public String generatePassword() {
        /**
         * Definisana pravila za generisanje lozinke
         */
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
        /**
         * Generisana duzina lozinke je 20 karaktera
         */
        String password = passwordGenerator.generatePassword(30, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
        return password;
    }
}
