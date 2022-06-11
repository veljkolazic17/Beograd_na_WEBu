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
import rs.psi.beogradnawebu.model.Korisnik;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class KorisnikDAOTest {

    @Autowired
    KorisnikDAO korisnikDAO;

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

    @Test
    void list() {
        assertEquals(korisnikDAO.list().size(), 5);
    }

    @Test
    void create() {
        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setKorisnickoime("comir");
        noviKorisnik.setSifra("comir123");
        noviKorisnik.setEmail("comir@gmail.com");
        noviKorisnik.setUloga(1);
        noviKorisnik.setEpredlog(0);
        korisnikDAO.create(noviKorisnik);
        assertNotNull(korisnikDAO.getUserByUsername("comir"));
    }

    @Test
    void get() {
        assertEquals(korisnikDAO.get(56).orElse(null).getKorisnickoime(), "marko");
    }

    @Test
    void update() {
        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setKorisnickoime("comir");
        noviKorisnik.setSifra("comir123");
        noviKorisnik.setEmail("comir@gmail.com");
        noviKorisnik.setUloga(1);
        noviKorisnik.setEpredlog(0);
        korisnikDAO.update(noviKorisnik, 56);
        Korisnik kor = korisnikDAO.get(56).orElse(null);
        assertEquals(kor.getKorisnickoime(), "comir");
        assertEquals(kor.getSifra(), "comir123");
        assertEquals(kor.getEmail(), "comir@gmail.com");
        assertEquals(kor.getEpredlog(), 0);
        assertEquals(kor.getUloga(), 1);
    }

    @Test
    void delete() {
        korisnikDAO.delete(56);
        Optional<Korisnik> kor = korisnikDAO.get(56);
        assertTrue(kor.isEmpty());
    }

    @Test
    void getUserByUsernameExists() {
        Korisnik kor = korisnikDAO.getUserByUsername("marko").orElse(null);
        assertEquals(kor.getKorisnickoime(), "marko");
    }

    @Test
    void getUserByUsernameNotExists() {
        Optional<Korisnik> kor = korisnikDAO.getUserByUsername("coje");
        assertTrue(kor.isEmpty());
    }

    @Test
    void getUserByEmailExists() {
        Korisnik kor = korisnikDAO.getUserByEmail("marko.mirkovic10@gmail.com").orElse(null);
        assertEquals(kor.getEmail(), "marko.mirkovic10@gmail.com");
    }

    @Test
    void getUserByEmailNotExists() {
        Optional<Korisnik> kor = korisnikDAO.getUserByEmail("marko");
        assertTrue(kor.isEmpty());
    }

    @Test
    void getByEpredlogFlag() {
        List<Korisnik> kor = korisnikDAO.getByEpredlogFlag();
        assertEquals(kor.size(), 3);
    }
}