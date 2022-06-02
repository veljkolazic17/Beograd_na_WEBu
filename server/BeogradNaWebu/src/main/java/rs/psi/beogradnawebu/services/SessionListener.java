/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.controller.FilterController;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SessionListener - klasa koja se koristi kao servlet za slusanje http requestova
 * @version 1.0
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Logger log= LoggerFactory.getLogger(SessionListener.class);

    /**
     * Kreiranje instace
     */
    public SessionListener() {
        super();

    }

    /**
     * Metoda koja se okine kada se napravai nova Http sesisja
     * @param sessionEvent
     *            the notification event
     */
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        log.info("-------NAPRAVLJENA SESIJA--------");
    }

    /**
     * Metoda koja se okine kada se ugasi http sesija i koja brise sadrzaj sesije
     * @param sessionEvent
     *            the notification event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        log.info("-------UNISTENA SESIJA--------");
        sessionEvent.getSession().invalidate();
    }

}