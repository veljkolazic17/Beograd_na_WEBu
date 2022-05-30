package rs.psi.beogradnawebu.configuration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//konfiguracija driver-a

@Configuration
public class SeleniumConfiguration {

    @PostConstruct
    void postConstuct() {
        System.setProperty("webdriver.chrome.driver", "server\\BeogradNaWebu\\drivers\\chromedriver.exe");
    }

    @Bean
    public ChromeDriver driver() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true); //za rad chroma u headless rezimu, tj. u pozadini
        return new ChromeDriver(options);
    }
}
