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
import rs.psi.beogradnawebu.model.LajkKomentara;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LajkKomentaraCDAOTest - klasa za testiranje LajkKomentaraCDAO klase
 * @version 1.0
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LajkKomentaraCDAOTest {

    @Autowired
    LajkKomentaraCDAO lajkKomentaraCDAO;

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
     * Test za dohvatanje liste svih lajkova na komentare iz baze
     */
    @Test
    void list() {
        assertEquals(lajkKomentaraCDAO.list().size(), 16);
    }

    /**
     * Test za dodavanje novog lajka na komentar u bazu
     */
    @Test
    void create() {
        LajkKomentara lajkKomentara = new LajkKomentara();
        lajkKomentara.setIdkomentar(99);
        lajkKomentara.setIdkorisnik(25);
        lajkKomentaraCDAO.create(lajkKomentara);
        LajkKomentara lajk = lajkKomentaraCDAO.get(new int[]{99, 25}).orElse(null);
        assertEquals(lajk.getIdkomentar(), 99);
        assertEquals(lajk.getIdkorisnik(), 25);
    }

    /**
     * Test za dohvatanje lajka na odredjeni komentar odredjene osobe (korisnika) iz baze
     */
    @Test
    void get() {
        LajkKomentara lajkKomentara = lajkKomentaraCDAO.get(new int[]{83, 25}).orElse(null);
        assertEquals(lajkKomentara.getIdkomentar(), 83);
        assertEquals(lajkKomentara.getIdkorisnik(), 25);
    }

    /**
     * Test za update lajka na komentar iz baze
     */
    @Test
    void update() {
        LajkKomentara lajkKomentara = new LajkKomentara();
        lajkKomentara.setIdkomentar(83);
        lajkKomentara.setIdkorisnik(25);
        lajkKomentaraCDAO.update(lajkKomentara, new int[]{99, 57});
        LajkKomentara lajk = lajkKomentaraCDAO.get(new int[]{99, 57}).orElse(null);
        assertEquals(lajk.getIdkomentar(), 99);
        assertEquals(lajk.getIdkorisnik(), 57);
    }

    /**
     * Test za brisanje lajka na komentar iz baze
     */
    @Test
    void delete() {
        lajkKomentaraCDAO.delete(new int[]{83, 25});
        Optional<LajkKomentara> lajk = lajkKomentaraCDAO.get(new int[]{83, 25});
        assertTrue(lajk.isEmpty());
    }
}