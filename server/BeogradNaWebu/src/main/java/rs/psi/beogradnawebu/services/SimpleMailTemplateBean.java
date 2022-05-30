package rs.psi.beogradnawebu.services;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SimpleMailTemplateBean {
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Ova poruka je generisana od strane računara. Nemojte odgovati na nju!\n\n%s\n\nSrećno pronalaženje smeštaja!\nVas BeogradNaWebu."
        );
        return message;
    }
}
