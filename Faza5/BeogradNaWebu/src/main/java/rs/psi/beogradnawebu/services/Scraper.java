/**
 * Jelena Lucic 2019/0268
 */

package rs.psi.beogradnawebu.services;

import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.SmestajDAO;

// broj_stana :
// 1 - 4zida - Stan
// 2 - 4zida - Kuca

/**
 * Scraper - apstraktna klasa scraper-a
 * @version 1.0
 */
public abstract class Scraper {
    protected final ChromeDriver driver;
    protected SmestajDAO smestaj;

    public Scraper(ChromeDriver driver, SmestajDAO smestaj){
        this.driver = driver;
        this.smestaj = smestaj;
    }

    /**
     * Apstraktna metoda za scrape-ovanje smestaja
     */
    public abstract void scrape();
}
