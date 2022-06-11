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
        driver = new ChromeDriver(/*options*/);
    }

    @Test
    public void brisanjeSvojihKomentara() {
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

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('.komentari:last-child>div>input:first-of-type').click()");

        String staraVrednost = (String)js.executeScript(" return document.querySelector('.komentari:nth-last-child(2)>input').value");

        js.executeScript("document.querySelector('.dugmadNaKomentarimaWrapper:last-of-type>input').click()");
        js.executeScript("document.querySelector('#prozorZaPotvrduBrisanjaKomentara>div>input:nth-child(2)').click()");

        List<WebElement> komentari = driver.findElements(By.className("komentari"));
        WebElement poslednji = komentari.get(komentari.size() - 1);
        String novaVrednost = (String)js.executeScript(" return document.querySelector('.komentari:nth-last-child(2)>input').value");

        assertNotEquals(staraVrednost, novaVrednost);

        driver.findElement(By.id("prikazStana")).click();
        //driver.findElement(By.tagName("input"));
    }
}