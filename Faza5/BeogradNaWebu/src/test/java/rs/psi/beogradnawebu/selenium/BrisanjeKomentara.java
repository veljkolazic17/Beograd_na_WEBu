package rs.psi.beogradnawebu.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BrisanjeKomentara {
    static private WebDriver driver;

    public BrisanjeKomentara(){
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
    }

    @Test
    public void brisanjeSvojihKomentara() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("http://localhost:8080");

        driver.findElement(By.id("username")).sendKeys("marko");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("marko");
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        List<WebElement> smestaji = driver.findElements(By.className("smestaji"));
        WebElement smestaj = smestaji.get(0);
        smestaj.click();

        driver.findElement(By.id("dugmeZaKomentare")).click();
        driver.findElement(By.id("noviKomentar")).click();

        WebElement noviKomentar = driver.findElement(By.tagName("textarea"));
        noviKomentar.sendKeys("Ja sam ovde novi");

        js.executeScript("document.querySelector('.komentari:last-child>div>input:first-of-type').click()");

        //2. jer je prozor u kome se ubacuje novi komentar takodje klase komentari
        String staraVrednost = (String)js.executeScript(" return document.querySelector('.komentari:nth-last-child(2)>input').value");

        js.executeScript("document.querySelector('.dugmadNaKomentarimaWrapper:last-of-type>input').click()");
        js.executeScript("document.querySelector('#prozorZaPotvrduBrisanjaKomentara>div>input:nth-child(2)').click()");

        String novaVrednost = (String)js.executeScript(
                "let prethodniKomentar = document.querySelector('.komentari:nth-last-child(2)>input');" +
                "if(prethodniKomentar == null) return null; else return prethodniKomentar.value;"
        );

        assertNotEquals(staraVrednost, novaVrednost);

        driver.close();
    }
}