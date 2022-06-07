/**
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * InfoChangeControllerTest
 * @version 1.0
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class InfoChangeControllerTest {

    @Autowired
    KorisnikDAO korisnikDAO;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Test - Nije validan email unet; provera ispravnosti rada validatora
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT", roles = {"KORISNIK"})
    void promenaEmaila_NevalidanEmail() throws Exception {
        mockMvc.perform(
                        post("/promena/email")
                        .param("stariEmail","")
                        .param("noviEmail","")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("uspesnaPromenaEmaila", is(false)));
    }

    /**
     * Test - Korisnik je uneo email koji ne odgovara njegovom nalogu; provera rada validatora
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT", roles = {"KORISNIK"})
    void promenaEmaila_PogresanEmail() throws Exception {
        mockMvc.perform(
                        post("/promena/email")
                                .param("stariEmail","veljkolazic117@gmail.com")
                                .param("noviEmail","platysmirz@gmail.com")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("uspesnaPromenaEmaila", is(false)));
    }

    /**
     * Test - korisnik je uneo dobre podatke i mail biva promenjen
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT", password = "123", roles = {"KORISNIK"})
    void promenaEmaila_EmailPromenjen() throws Exception {

        String dummyMail = "dummymail@dummy.com";

        mockMvc.perform(
                        post("/promena/email")
                                .param("stariEmail","matejacraft@gmail.com")
                                .param("noviEmail",dummyMail)
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("uspesnaPromenaEmaila", is(true)));
        assertEquals(korisnikDAO.getUserByUsername("MAT").orElse(null).getEmail(),dummyMail);
    }

    /**
     * Test - korisnik unosi sifru koja se ne podudara sa ispravnom sifrom
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT",password = "123",roles = {"KORISNIK"})
    void promenaSifre_staraSifraNijeTacna() throws Exception {
        mockMvc.perform(
                        post("/promena/sifra")
                                .param("staraSifra","boba")
                                .param("novaSifra","danko")
                                .param("ponovljenaNovaSifra","danko")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("uspesnaPromenaSifre", is(false)));
    }

    /**
     * Test - korisnik unosi ispravnu sifura, ali se nova sifra ne podudara sa ponovljenom
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT",password = "123",roles = {"KORISNIK"})
    void promenaSifre_NovaSifraNijeLepoPonovljena() throws Exception {
        mockMvc.perform(
                        post("/promena/sifra")
                                .param("staraSifra","123")
                                .param("novaSifra","danko")
                                .param("ponovljenaNovaSifra","branko")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("uspesnaPromenaSifre", is(false)));
    }

    /**
     * Test - korisnik je uneo sve ispravne podatke i sifra biva promenjena
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT",password = "123",roles = {"KORISNIK"})
    void promenaSifre_SifraPromenjena() throws Exception {

        String dummySifra = "boba";

        mockMvc.perform(
                        post("/promena/sifra")
                                .param("staraSifra","123")
                                .param("novaSifra",dummySifra)
                                .param("ponovljenaNovaSifra",dummySifra)
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/logout"))
                .andExpect(flash().attribute("uspesnaPromenaSifre", nullValue()));

        assertEquals(korisnikDAO.getUserByUsername("MAT").orElse(null).getSifra(),dummySifra);
    }


    /**
     * Test - korisnik se nije pretplatio na servis te ce API poziv promeniti stanje pretplate
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT",password = "123",roles = {"KORISNIK"})
    void promenaPretplate_EpredlogNula() throws Exception {

        int dummyEpredlogPocetak = 0;
        int dummyEpredlogKraj = 1;

        Korisnik dummyKorisnik = korisnikDAO.getUserByUsername("MAT").orElse(null);
        dummyKorisnik.setEpredlog(dummyEpredlogPocetak);
        korisnikDAO.update(dummyKorisnik, (int)dummyKorisnik.getIdkorisnik());

        mockMvc.perform(post("/promena/pretplata"))
                .andExpect(status().is(200));

        assertEquals(korisnikDAO.getUserByUsername("MAT").orElse(null).getEpredlog(),dummyEpredlogKraj);
    }


    /**
     * Test - korisnik se pretplatio na servis te ce API poziv promeniti stanje pretplate
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "MAT",password = "123",roles = {"KORISNIK"})
    void promenaPretplate_EpredlogJedan() throws Exception {

        int dummyEpredlogPocetak = 1;
        int dummyEpredlogKraj = 0;

        Korisnik dummyKorisnik = korisnikDAO.getUserByUsername("MAT").orElse(null);
        dummyKorisnik.setEpredlog(dummyEpredlogPocetak);
        korisnikDAO.update(dummyKorisnik, (int)dummyKorisnik.getIdkorisnik());

        mockMvc.perform(post("/promena/pretplata"))
                .andExpect(status().is(200));

        assertEquals(korisnikDAO.getUserByUsername("MAT").orElse(null).getEpredlog(),dummyEpredlogKraj);
    }

}