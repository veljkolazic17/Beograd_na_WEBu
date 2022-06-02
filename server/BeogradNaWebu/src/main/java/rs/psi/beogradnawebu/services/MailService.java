/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * MailService - klasa zaduzena za slanje mejlova korisnicima
 * @version 1.0
 */
@Service
public class MailService {

    private final JavaMailSender emailSender;

    /**
     * Moze da se koristi i drugi template, ovo je defaultni. Ako je potrebno ugraditi neki drugi
     * neophodno je dodati novi Bean u SimpleMailTemplateBean klasu!
     */
    private final SimpleMailMessage simpleMailMessage;
    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Kreiranje instance
     * @param emailSender
     * @param simpleMailMessage
     */
    public MailService(JavaMailSender emailSender, SimpleMailMessage simpleMailMessage){
        this.emailSender = emailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    /**
     * Metoda koja salje majl korisniku sa mejlom to, sa naslovom subject i tekstom poruke text
     * Nije moguce dodati attachment ovom metodom, ako je to potrebno, implementiraj je
     * @param to
     * @param subject
     * @param text
     */
    public void sendEmail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(String.format(simpleMailMessage.getText(),text));
        emailSender.send(message);
    }
}

