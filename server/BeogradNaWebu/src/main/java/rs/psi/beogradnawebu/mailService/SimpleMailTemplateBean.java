package rs.psi.beogradnawebu.mailService;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SimpleMailTemplateBean {
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Ova poruka je generisana od strane racunara. Nemojte odgovati na nju!\n\n%s\n\nSrecno pronalazenje smestaja!\nVas BeogradNaWebu."
        );
        return message;
    }
}
