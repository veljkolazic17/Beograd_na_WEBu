/**
 * Jelena Lucic 2019/0268
 */

package rs.psi.beogradnawebu.services;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * Kuca4Zida - Scraper za sajt 4Zida (scrape-uje samo kuce)
 * @version 1.0
 */
@Service
public class Kuca4Zida extends Scraper4Zida {

    /**
     * Metoda za pozivanje srcapera iz scheduler-a
     * @return
     */
    public List<Smestaj> callScraper() {
        scrape(); // automatsko pozivanje metode scrape
        smestaj.deleteWithFalseTag(2); // brisanje kuca koji se ne nalaze vise na sajtu
        smestaj.setAllTags(2);// ponistavanje tagova
        List<Smestaj> list = allAcc;
        allAcc.clear();
        return list;
    }

    /**
     * Kreiranje nove instance
     * @param driver
     * @param smestaj
     */
    public Kuca4Zida(ChromeDriver driver, SmestajDAO smestaj) {
        super(driver, smestaj);
        URL = "https://www.4zida.rs/izdavanje-kuca/beograd";
    }

    /**
     * Pravljenje nove kuce
     * @param href
     * @param src
     * @return
     */
    @Override
    protected Smestaj makeNew(String href, String src) {
        HashMap<String, String> attributes = getAttributes(); // atributi odredjene kuce

        String location;
        try {
            location = driver.findElementByClassName("location").getText(); // lokacija kuce
        } catch (Exception e) {
            location = "";
        }

        Smestaj noviSmestaj = new Smestaj();
        noviSmestaj.setOrgPutanja(href);
        noviSmestaj.setLokacija(location);
        noviSmestaj.setBrojSoba(getBrojSoba(attributes.get("Broj soba:")));
        noviSmestaj.setSpratonost(-1);
        noviSmestaj.setImaLift(
                attributes.get("Lift:") == null ? 0 : 1
        );
        noviSmestaj.setKvadratura(getKvadratura(attributes.get("Površina:")));
        noviSmestaj.setCena(getCena(attributes.get("Cena:")));
        noviSmestaj.setIdtipSmestaja(2); // kuca
        noviSmestaj.setPostoji(1);
        noviSmestaj.setBrojSajta(2);
        noviSmestaj.setSlika(src);

        return  noviSmestaj;
    }
}
