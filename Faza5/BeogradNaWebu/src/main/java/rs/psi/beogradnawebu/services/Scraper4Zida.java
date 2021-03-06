/**
 * Jelena Lucic 2019/0268
 */

package rs.psi.beogradnawebu.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Scraper4Zida - Apstraktna klasa za scraping sajta 4Zida
 * @version 1.0
 */

@Service
public abstract class Scraper4Zida extends Scraper {
    protected String URL;
    private ArrayList<String> tabs;
    protected List<Smestaj> allAcc;

    /**
     * Kreiranje nove instance
     * @param driver
     * @param smestaj
     */
    public Scraper4Zida(ChromeDriver driver, SmestajDAO smestaj) {
        super(driver, smestaj);
        allAcc = new ArrayList<>();
    }

    /**
     * Metoda za dohvatanje poslednje stranice do koje se radi scraping
     * @return
     */
    private int getLastPage() { // dohvata broj poslednje stranice
        driver.get(URL);
        WebElement pagePart = driver.findElementByClassName("pagination");
        List<WebElement> pages = pagePart.findElements(By.className("page-link"));

        List<String> numPages = new ArrayList<>();

        for (WebElement page: pages) {
            numPages.add(page.getText());
        }

        return Integer.parseInt(numPages.get(numPages.size() - 2));
    }

    /**
     * Metoda za proveru rezultata pretrage
     * @return
     */
    private boolean check() { // provera da li postoje rezultati pretrage
        try {
            driver.get(URL);
            WebElement we = driver.findElementByClassName("search-results");
            String search = we.findElement(By.tagName("p")).getText();
            return !search.equals("Nema rezultata za ove kriterijume pretrage");
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Metoda za dohvatanje atributa odredjenog smestaja
     * @return
     */
    protected HashMap<String, String> getAttributes() { // dohvata sve atribute za odredjeni stan
        WebElement mainPartAcc = driver.findElementById("meta-data");
        List<WebElement> labels = mainPartAcc.findElements(By.className("label"));
        List<WebElement> values = mainPartAcc.findElements(By.className("value"));

        HashMap<String, String> allAttributes = new HashMap<>();
        for (int i = 0; i < labels.size(); i++) {
            allAttributes.put(labels.get(i).getText(), values.get(i).getText());
        }
        return allAttributes;
    }

    /**
     * Metoda za dohvatanje broja soba smestaja na osnovu parametra
     * @param value
     * @return
     */
    protected double getBrojSoba(String value) {
        if(value == null) return -1;
        String[] splitValues = value.split(" ");
        return Double.parseDouble(splitValues[0]);
    }

    /**
     * Metoda za dohvatanje cene smestaja na osnovu parametra
     * @param value
     * @return
     */
    protected double getCena(String value) {
        if(value == null) return -1;
        String[] splitValues = value.split(" ");
        String[] splitByDot = splitValues[0].split("\\.");
        if(splitByDot.length < 2) return Double.parseDouble(splitValues[0]); // proveriti
        return Integer.parseInt(splitByDot[0]) * 1000 + Integer.parseInt(splitByDot[1]);
    }

    /**
     * Metoda za dohvatanje kvadrature smestaja na osnovu parametra
     * @param value
     * @return
     */
    protected int getKvadratura(String value) {
        if(value == null) return -1;
        return Integer.parseInt(value.substring(0, value.length() - 2));
    }

    /**
     * Apstraktna metoda za pravljenje novog smestaja
     * @param href
     * @param src
     * @return
     */
    protected abstract Smestaj makeNew(String href, String src);

    /**
     * Metoda za azuriranje baze prilikom pronalaska novog smestaja
     * @param href
     * @param src
     */
    private void updateDatabase(String href, String src) {
        if(!smestaj.checkIfExist(href)) {
            Smestaj novi = makeNew(href, src);
            allAcc.add(novi);
            smestaj.create(novi);
        }
    }

    /**
     * Metoda za scrape-ovanje pojedinacne stranice sa smestajem
     * @param page
     */
    private void scrapePage(int page) {
        driver.get(URL + "?strana=" + page);

        WebElement mainPart = driver.findElementByClassName("preview-cards-container"); // deo sa svim stanovima na jednoj stranici
        List<WebElement> accList = mainPart.findElements(By.className("meta-container")); // pojedinacni delovi stanova
        List<WebElement> picList = mainPart.findElements(By.className("preview-img"));

        if(tabs == null){
            driver.executeScript("window.open('about:blank','_blank');"); // otvara novi prazan tab
            tabs = new ArrayList<> (driver.getWindowHandles()); // smesta sve tabove trenutno otvorene u listu
        }

        for (int i = 0; i < accList.size(); i++) {
            driver.switchTo().window(tabs.get(0));

            WebElement link = accList.get(i).findElement(By.tagName("a"));
            String href = link.getAttribute("href"); // dohvatanje linka svakog stana

            String src;
            try {
                src = picList.get(i).getAttribute("src");
            } catch (Exception e) {
                src = "";
            }

            driver.switchTo().window(tabs.get(1));

            driver.get(href); // otvara novi link stana

            updateDatabase(href, src); // azuriranje baze
        }
        driver.switchTo().window(tabs.get(0)); // povratak na glavnu stranu
    }

    /**
     * Meroda za scrape-ovanje svih smestaja
     */
    @Override
    public void scrape() {
        try {
            if(!check()) {
                return;
            }

            int lastPage = getLastPage();

            for (int i = 1; i < lastPage; i++) {
                scrapePage(i);
            }

            System.out.println("zavrsen scraping");

        }  catch (Exception e) {
            System.out.println("Neispravan scraping");
        }
    }
}
