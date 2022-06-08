/**
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.selenium;


import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class FilterPageTest {

     static WebDriver webDriver;

    public FilterPageTest(){
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        webDriver = new ChromeDriver(options);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Novi Beograd", "Zemun", "Zvezdara"})
    void testFilterPageByLokacija(String lokacija){
        webDriver.get("http://localhost:8080/pregledsmestaja/0");

        webDriver.findElement(By.id("dugmeFilteri")).click();
        Select selectLokacija = new Select(webDriver.findElement(By.id("Lokacija")));
        selectLokacija.selectByValue(lokacija);
        webDriver.findElement(By.id("potvrdiFiltereDugme")).click();


        while (true){
            List<WebElement> webElementList = webDriver.findElements(By.className("opisSmestaja"));
            if(webElementList.size() == 0)
                break;
            for(WebElement webElement : webElementList){
                assertTrue(webElement.getText().contains(lokacija));
            }
            webDriver.findElement(By.className("stranicaDesno")).click();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Garsonjera", "Jednosoban", "Jednoiposoban","Dvosoban","Dvoiposoban"})
    void testFilterPageByBrojSoba(String brojsoba){
        webDriver.get("http://localhost:8080/pregledsmestaja/0");

        webDriver.findElement(By.id("dugmeFilteri")).click();
        Select selectLokacija = new Select(webDriver.findElement(By.id("BrojSoba")));
        selectLokacija.selectByValue(brojsoba);
        webDriver.findElement(By.id("potvrdiFiltereDugme")).click();


        while (true){
            List<WebElement> webElementList = webDriver.findElements(By.className("opisSmestaja"));
            if(webElementList.size() == 0)
                break;
            for(WebElement webElement : webElementList){
                assertTrue(webElement.getText().contains(projekcija(brojsoba)));
            }
            webDriver.findElement(By.className("stranicaDesno")).click();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"20:30", "40:70", "70:100","110:200","220:400"})
    void testFilterPageByKvadratura(String kvadratura){
        webDriver.get("http://localhost:8080/pregledsmestaja/0");

        webDriver.findElement(By.id("dugmeFilteri")).click();

        String[] parsedKvadratura = kvadratura.split(":");
        webDriver.findElement(By.id("KvadraturaOd")).sendKeys(parsedKvadratura[0]);
        webDriver.findElement(By.id("KvadraturaDo")).sendKeys(parsedKvadratura[1]);

        webDriver.findElement(By.id("potvrdiFiltereDugme")).click();

        String patternString = "\\s([0-9]+)m";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;

        while (true){
            List<WebElement> webElementList = webDriver.findElements(By.className("opisSmestaja"));
            if(webElementList.size() == 0)
                break;
            for(WebElement webElement : webElementList){

                matcher = pattern.matcher(webElement.getText());
                matcher.find();
                String result = matcher.group(0);
                int resultint = Integer.parseInt(result.substring(1,result.length()-1));
                boolean uslov = resultint >= Integer.parseInt(parsedKvadratura[0]) && resultint <= Integer.parseInt(parsedKvadratura[1]);
                assertTrue(uslov);
            }
            webDriver.findElement(By.className("stranicaDesno")).click();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"100:200", "210:300", "310:400","410:500","510:600"})
    void testFilterPageByCena(String cena){
        webDriver.get("http://localhost:8080/pregledsmestaja/0");

        webDriver.findElement(By.id("dugmeFilteri")).click();

        String[] parsedCena = cena.split(":");
        webDriver.findElement(By.id("CenaOd")).clear();
        webDriver.findElement(By.id("CenaDo")).clear();
        webDriver.findElement(By.id("CenaOd")).sendKeys(parsedCena[0]);
        webDriver.findElement(By.id("CenaDo")).sendKeys(parsedCena[1]);

        webDriver.findElement(By.id("potvrdiFiltereDugme")).click();

        String patternString = "Cena:\\s([0-9]+)\\.0\\s€";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;

        while (true) {
            List<WebElement> webElementList = webDriver.findElements(By.className("opisSmestaja"));
            if (webElementList.size() == 0)
                break;
            for (WebElement webElement : webElementList) {

                matcher = pattern.matcher(webElement.getText());
                matcher.find();
                String result = matcher.group(0);
                int resultint = Integer.parseInt(result.substring(6, result.length() - 4));
                boolean uslov = resultint >= Integer.parseInt(parsedCena[0]) && resultint <= Integer.parseInt(parsedCena[1]);
                assertTrue(uslov);
            }
            webDriver.findElement(By.className("stranicaDesno")).click();
        }
    }

    @AfterAll
    static void endDriver(){
        webDriver.close();
    }

    String projekcija(String brojsoba){
        switch (brojsoba){
            case "Garsonjera" -> {
                return " 0.5";
            }
            case "Jednosoban" -> {
                return " 1.0";
            }
            case "Jednoiposoban" -> {
                return " 1.5";
            }
            case "Dvosoban" -> {
                return " 2.0";
            }
            case "Dvoiposoban" -> {
                return " 2.5";
            }
        }
        return "";
    }
}
