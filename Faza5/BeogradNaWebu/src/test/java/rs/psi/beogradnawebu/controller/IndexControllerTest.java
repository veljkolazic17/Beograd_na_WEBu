/**
 * Jelena Lucic 2019/0268
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.psi.beogradnawebu.dao.KorisnikDAO;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * IndexControllerTest - klasa za testiranje IndexController klase
 * @version 1.0
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class IndexControllerTest {

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

    /**
     * Parametrizovan test za neuspesnu prijavu korisnika
     * @param username
     * @param sifra
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
            "'', 123",
            "Platon, ''",
            "Platon, 1234",
            "Platon123, 123"
    })
    void loginNeuspesan(String username, String sifra) throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", sifra)
                )
                .andExpect(status().is(200));
    }

    /**
     * Test za uspesnu prijavu korisnika
     * @throws Exception
     */
    @Test
    void loginUspesan() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "marko")
                        .param("password", "marko")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    /**
     * Parametrizovan test za neuspesnu registraciju korisnika
     * @param username
     * @param sifra
     * @param ponovoSifra
     * @param email
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
            "Platon, 123, 123, zika@gmail.com",
            "'', 123, 123, zika@gmail.com",
            "Zika, '', 123, zika@gmail.com",
            "Zika, 123, '', zika@gmail.com",
            "Zika, 123, 123, ''",
            "Zika, 123, 1234, zika@gmail.com",
            "Zika, 123, 123, zikagmail.com",
            "Zika, ovojeveomavelikasifraduzaodtridesetkaraktera, ovojeveomavelikasifraduzaodtridesetkaraktera, zika@gmail.com",
            "ovojeveomadugackoimeduzeodtridesetkaraktera, 123, 123, zika@gmail.com"
    })
    void registracijaNeuspesna(String username, String sifra, String ponovoSifra, String email) throws Exception {
        mockMvc.perform(post("/registracija")
                .param("korime", username)
                .param("sifra", sifra)
                .param("potvrdaSifre", ponovoSifra)
                .param("email", email)
                )
                .andExpect(status().is(200));
    }

    /**
     * Test za uspesnu registraciju korisnika
     * @throws Exception
     */
    @Test
    void registracijaUspesna() throws Exception {
        mockMvc.perform(post("/registracija")
                .param("korime", "coje")
                .param("sifra", "123")
                .param("potvrdaSifre", "123")
                .param("email", "coje123@gmail.com")
        )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"));

        assertTrue(korisnikDAO.getUserByUsername("coje").orElse(null) != null);
    }
}