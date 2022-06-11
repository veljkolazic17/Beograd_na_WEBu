package rs.psi.beogradnawebu.selenium;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

public class PrikazKonkretnogSmestaja {

    private static WebDriver driver;
    private JavascriptExecutor js;

    public PrikazKonkretnogSmestaja() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void brisanjeKomentara() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("username")).sendKeys("marko");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("marko");
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        List<WebElement> smestaji = driver.findElements(By.cssSelector(".smestaji"));
        List<WebElement> opisi = driver.findElements(By.className("opisSmestaja"));

        boolean notEqual = false;
        for(int i = 0; i < smestaji.size(); i++) {

            js.executeScript("document.getElementsByClassName('smestaji')[" + i + "].click()");

            String tekstKonkretnogSmestaja = driver.findElement(By.id("opisKonkretnogSmestaja")).getText();
            HashMap<String, String> konkretniSmestajMapa = new HashMap<String, String>();
            String tokeniKonkretnogSmestaja[] = tekstKonkretnogSmestaja.split("\n");
            for(String token: tokeniKonkretnogSmestaja) {
                String vrednost[] = token.split(":");

                if(vrednost[0].equals("Ima lift")) continue;

                konkretniSmestajMapa.put(vrednost[0].trim(), vrednost[1].trim());
            }

            String tekstOpisa = opisi.get(i).getText();
            String tokeniSmestaja[] = tekstOpisa.split("\n");
            for(String token: tokeniSmestaja) {
                String vrednost[] = token.split(":");
                vrednost[1] = vrednost[1].trim();

                String vrednostMapa[] = {vrednost[0].trim(), konkretniSmestajMapa.get(vrednost[0])};

                if(!vrednostMapa[1].equals(vrednost[1])) {
                    System.out.println(vrednost[1]);
                    System.out.println(vrednostMapa[1]);
                    notEqual = true;
                    break;
                }
            }

            if(notEqual) break;

            js.executeScript("document.getElementById('prikazStana').click()");
        }

        assertEquals(false, notEqual);

        driver.close();
    }

}
