/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.services;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * SimpleMailTemplateBean - bean koji se koristi kao template za kreiranje sadrzaja mejla
 * @version 1.0
 */
@Component
public class SimpleMailTemplateBean {
    /**
     * Metoda koja vraca template tekst
     * @return
     */
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Ova poruka je generisana od strane računara. Nemojte odgovati na nju!\n\n%s\n\nSrećno pronalaženje smeštaja!\nVas BeogradNaWebu."
        );
        return message;
    }
}
