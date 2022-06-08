/**
 * Jelena Lucic 2019/0268
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.psi.beogradnawebu.model.Komentar;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * KomentarDAOTest - klasa za testsiranje KomentarDAO klase
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class KomentarDAOTest {

    @Autowired
    KomentarDAO komentarDAO;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    /**
     * Metoda koja se poziva pre svakog testa - inicijalizacija promenljive mockMvc
     */
    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    /**
     * Test za dohvatanje svih komentara
     */
    @Test
    void list() {
        assertEquals(komentarDAO.list().size(), 19);
    }

    /**
     * Test za upis novog komentara u bazu
     */
    @Test
    void create() {
        Komentar noviKom = new Komentar();
        noviKom.setIdsmestaj(500);
        noviKom.setBroj_lajkova(0);
        noviKom.setTekstKomentara("Odlicno");
        noviKom.setIdkorisnik(25);
        komentarDAO.create(noviKom);
        assertEquals(komentarDAO.list().size(), 20);
    }

    /**
     * Test za dohvatanje komentara sa odredjenim id iz baze
     */
    @Test
    void get() {
        Komentar kom = komentarDAO.get(80).orElse(null);
        assertEquals(kom.getIdkomentar(), 80);
    }

    /**
     * Test za update komentara sa odredjenim id iz baze
     */
    @Test
    void update() {
        Komentar noviKom = new Komentar();
        noviKom.setIdkomentar(89);
        noviKom.setIdsmestaj(341);
        noviKom.setBroj_lajkova(0);
        noviKom.setTekstKomentara("Odlicno");
        noviKom.setIdkorisnik(57);
        komentarDAO.update(noviKom, 89);
        Komentar kom = komentarDAO.get(89).orElse(null);
        assertEquals(kom.getTekstKomentara(), "Odlicno");
    }

    /**
     * Test za brisanje komentara sa odredjenim id iz baze
     */
    @Test
    void delete() {
        komentarDAO.delete(89);
        Optional<Komentar> kom = komentarDAO.get(89);
        assertNull(kom);
    }

    /**
     * Test za dohvatanje max id komentara iz baze
     */
    @Test
    void maxID() {
        assertEquals(komentarDAO.maxID(), 99);
    }

    /**
     * Test za dohvatanje komentara sa odredjenim id smestaja iz baze
     */
    @Test
    void allKomentar() {
        assertEquals(komentarDAO.allKomentar(337).size(), 4);
    }
}