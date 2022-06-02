/**
 * Jelena Lucic 2019/0268
 */

package rs.psi.beogradnawebu.configuration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//konfiguracija driver-a

/**
 * SeleniumConfiguration - klasa za konfiguraciju driver-a kod scrapera
 * @version 1.0
 */
@Configuration
public class SeleniumConfiguration {

    /**
     * Metoda za podesavanje property-a za driver
     */
    @PostConstruct
    void postConstuct() {
        System.setProperty("webdriver.chrome.driver", "server\\BeogradNaWebu\\drivers\\chromedriver.exe");
    }

    /**
     * Nova instanca drivera u headless rezimu
     * @return
     */
    @Bean
    public ChromeDriver driver() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true); //za rad chroma u headless rezimu, tj. u pozadini
        return new ChromeDriver(options);
    }
}
