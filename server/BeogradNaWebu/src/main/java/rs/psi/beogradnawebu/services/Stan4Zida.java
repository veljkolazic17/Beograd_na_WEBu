package rs.psi.beogradnawebu.services;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class Stan4Zida extends Scraper4Zida {


    public void callScraper() {
        scrape(); // automatsko pozivanje metode scrape
        smestaj.deleteWithFalseTag(1); // brisanje stanova koji se ne nalaze vise na sajtu
        smestaj.setAllTags(1);// ponistavanje tagova
    }

    public Stan4Zida(ChromeDriver driver, SmestajDAO smestaj) {
        super(driver, smestaj);
        URL = "https://www.4zida.rs/izdavanje-stanova/beograd";
    }

    protected int getSpratnost(String value) {
        if(value == null) return 0;
        String[] splitValues = value.split("/");
        if(splitValues.length < 2) return 0; // proveriti
        if(splitValues[0].equals("suteren")
                || splitValues[0].equals("visoko prizemlje")
                || splitValues[0].equals("nisko prizemlje")
                || splitValues[0].equals("prizemlje")) return 0;
        else return Integer.parseInt(splitValues[0]);
    }

    @Override
    protected Smestaj makeNew(String href, String src) {
        HashMap<String, String> attributes = getAttributes(); // atributi odredjenog stana
        String location = driver.findElementByClassName("location").getText(); // lokacija stana

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
