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
 * Stan4Zida - Scraper za sajt 4Zida (scrape-uje samo stanove)
 * @version 1.0
 */
@Service
public class Stan4Zida extends Scraper4Zida {

    /**
     * Metoda za pozivanje scraper-a iz scheduler-a
     * @return
     */
    public List<Smestaj> callScraper() {
        scrape(); // automatsko pozivanje metode scrape
        smestaj.deleteWithFalseTag(1); // brisanje stanova koji se ne nalaze vise na sajtu
        smestaj.setAllTags(1);// ponistavanje tagova
        List<Smestaj> list = allAcc;
        allAcc.clear();
        return list;
    }

    /**
     * Kreiranje nove instance
     * @param driver
     * @param smestaj
     */
    public Stan4Zida(ChromeDriver driver, SmestajDAO smestaj) {
        super(driver, smestaj);
        URL = "https://www.4zida.rs/izdavanje-stanova/beograd";
    }

    /**
     * Metoda za dohvatanje spratnosti stana na osnovu parametra
     * @param value
     * @return
     */
    protected int getSpratnost(String value) {
        if(value == null) return -1;
        String[] splitValues = value.split("/");
        if(splitValues.length < 2) return 0; // proveriti
        if(splitValues[0].equals("suteren")
                || splitValues[0].equals("visoko prizemlje")
                || splitValues[0].equals("nisko prizemlje")
                || splitValues[0].equals("prizemlje")) return 0;
        else return Integer.parseInt(splitValues[0]);
    }

    /**
     * Metoda za pravljenje novog stana
     * @param href
     * @param src
     * @return
     */
    @Override
    protected Smestaj makeNew(String href, String src) {
        HashMap<String, String> attributes = getAttributes(); // atributi odredjenog stana

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
        noviSmestaj.setSpratonost(getSpratnost(attributes.get("Spratnost:")));
        noviSmestaj.setImaLift(
                attributes.get("Lift:") == null ? 0 : 1
        );
        noviSmestaj.setKvadratura(getKvadratura(attributes.get("Površina:")));
        noviSmestaj.setCena(getCena(attributes.get("Cena:")));
        noviSmestaj.setIdtipSmestaja(1); // stan
        noviSmestaj.setPostoji(1);
        noviSmestaj.setBrojSajta(1);
        noviSmestaj.setSlika(src);

        return noviSmestaj;
    }
}
