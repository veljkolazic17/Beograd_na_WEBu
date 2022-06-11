package rs.psi.beogradnawebu.selenium;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class EmailChangeTest {
    WebDriver webDriver;
    ChromeOptions options;
    public EmailChangeTest(){
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        options = new ChromeOptions();
//        options.setHeadless(true);

    }

    @Test
    void emailChangeTest(){
        webDriver = new ChromeDriver(options);
        webDriver.get("http://localhost:8080");
        WebElement username = webDriver.findElement(By.id("username"));
        WebElement password = webDriver.findElement(By.name("password"));
        username.sendKeys("MAT");
        password.sendKeys("123");
        WebElement login = webDriver.findElement(By.cssSelector("#desniPanel > form > table > tbody > tr:nth-child(5) > td:nth-child(2) > input"));
        login.click();
        WebElement nalogDugme = webDriver.findElement(By.id("dugmeNalog"));
        nalogDugme.click();
        WebElement promenaEmailaDugme = webDriver.findElement(By.id("promenaEmailaDugme"));
        promenaEmailaDugme.click();
        WebElement stariEmail = webDriver.findElement(By.id("StariEmail"));
        stariEmail.sendKeys("matejacraft@gmail.com");
        WebElement noviEmail = webDriver.findElement(By.id("NoviEmail"));
        noviEmail.sendKeys("matijacraft@gmail.com");
        WebElement confirm = webDriver.findElement(By.cssSelector(".prozorZaPotvrduDugmadWrapper:nth-child(3) > input:nth-child(2)"));
        confirm.click();
        Assertions.assertEquals("Uspešno ste promenili Vaš email.",webDriver.switchTo().alert().getText());
        webDriver.switchTo().alert().accept();
        WebElement isEqual = webDriver.findElement(By.cssSelector("#nalogPanel > p:nth-child(2)"));
        Assertions.assertEquals("Email:matij...",isEqual.getText());
        WebElement promenaEmailaDugme2 = webDriver.findElement(By.id("promenaEmailaDugme"));
        promenaEmailaDugme2.click();
        WebElement stariEmail2 = webDriver.findElement(By.id("StariEmail"));
        stariEmail2.sendKeys("matijacraft@gmail.com");
        WebElement noviEmail2 = webDriver.findElement(By.id("NoviEmail"));
        noviEmail2.sendKeys("matejacraft@gmail.com");
        WebElement confirm2 = webDriver.findElement(By.cssSelector(".prozorZaPotvrduDugmadWrapper:nth-child(3) > input:nth-child(2)"));
        confirm2.click();
        webDriver.switchTo().alert().accept();
        WebElement isEqual2 = webDriver.findElement(By.cssSelector("#nalogPanel > p:nth-child(2)"));
        Assertions.assertEquals("Email:matej...",isEqual2.getText());
        webDriver.quit();
    }
}
