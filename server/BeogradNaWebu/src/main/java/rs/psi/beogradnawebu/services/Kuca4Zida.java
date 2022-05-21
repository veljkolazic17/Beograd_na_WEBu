package rs.psi.beogradnawebu.services;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Smestaj;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class Kuca4Zida extends Scraper4Zida {

    @PostConstruct
    void postConstuct() {
        scrape(); // automatsko pozivanje metode scrape
        smestaj.deleteWithFalseTag(2); // brisanje kuca koji se ne nalaze vise na sajtu
        smestaj.setAllTags(2);// ponistavanje tagova
    }

    public Kuca4Zida(ChromeDriver driver, SmestajDAO smestaj) {
        super(driver, smestaj);
        URL = "https://www.4zida.rs/izdavanje-kuca/beograd";
    }

    @Override
    protected Smestaj makeNew(String href, String src) {
        HashMap<String, String> attributes = getAttributes(); // atributi odredjene kuce
        String location = driver.findElementByClassName("location").getText(); // lokacija kuce

        Smestaj noviSmestaj = new Smestaj();
        noviSmestaj.setOrgPutanja(href);
        noviSmestaj.setLokacija(location);
        if(getBrojSoba(attributes.get("Broj soba:")) != -1) noviSmestaj.setBrojSoba(getBrojSoba(attributes.get("Broj soba:")));
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
