/**
 * Jelena Lucic 2019/0268
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * RecommendedControllerTest - klasa za testiranje RecommendedController klase
 * @version 1.0
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RecommendedControllerTest {

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
     * Test za prikaz predlozenih smestaja
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "marko", roles = {"KORISNIK"})
    void listSmestaj() throws Exception {
        mockMvc.perform(
                get("/pregledpredlozenihsmestaja"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("prikazPredlozenih", is(true)));
    }
}