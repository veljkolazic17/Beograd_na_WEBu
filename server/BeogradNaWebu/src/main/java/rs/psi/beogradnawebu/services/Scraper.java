package rs.psi.beogradnawebu.services;

import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.SmestajDAO;

// broj_stana :
// 1 - 4zida - Stan
// 2 - 4zida - Kuca
// 3 - drugo (neki drugi sajt)

@Service
@AllArgsConstructor
public abstract class Scraper {
    protected final ChromeDriver driver;
    protected SmestajDAO smestaj;
    public Scraper(ChromeDriver driver, SmestajDAO smestaj) {
        this.driver = driver;
        this.smestaj = smestaj;
    }
    public abstract void scrape();
}
