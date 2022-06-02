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


@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Logger log= LoggerFactory.getLogger(SessionListener.class);

    public SessionListener() {
        super();

    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        log.info("-------NAPRAVLJENA SESIJA--------");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        log.info("-------UNISTENA SESIJA--------");
        sessionEvent.getSession().invalidate();
    }

}