package rs.psi.beogradnawebu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LikeControllerTest {

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

    @Autowired
    private SmestajDAO smestajDAO;

    @ParameterizedTest
    @CsvSource({
            "337, marko, marko, 200, true", // sistem dohvata smestaj koji je lajkovan(LK)
            "337, Platon, 123, 200, false", // sistem dohvata smestaj koji nije lajkovan(LK)
            "337, '', '', 302, false"       // sistem dohvata smestaj za strano lice(NK)
    })
    void isLiked(int idSmestaja, String korisnickoIme, String sifra, int statusniKod, boolean lajkovan) throws Exception {

        MvcResult odgovor = null;
        if(korisnickoIme.length() != 0) {
            MvcResult auth = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .param("username", korisnickoIme)
                    .param("password", sifra)).andReturn();

            MockHttpSession sesija = (MockHttpSession) auth.getRequest().getSession();

            odgovor = this.mockMvc.perform(MockMvcRequestBuilders.get("/isliked/" + korisnickoIme +"/" + idSmestaja).session(sesija))
                    .andExpect(status().is(statusniKod)).andReturn();
        } else {
            odgovor = this.mockMvc.perform(MockMvcRequestBuilders.get("/isliked//" + idSmestaja))
                    .andExpect(status().is(statusniKod)).andReturn();
        }

        assertEquals(lajkovan, Boolean.parseBoolean(odgovor.getResponse().getContentAsString()));

    }

    @ParameterizedTest
    @CsvSource({
            "337, marko, marko, 200, true, 1", // korisnik bez administratorskih prava anlajkuje smestaj(LK)
            "340, marko, marko, 200, false, 2",  // korisnik bez administratorskih prava lajkuje smestaj(LK)
            "345, Platon, 123, 200, true, 2",  // korisnik sa administratorskim pravima anlajkuje smestaj(LK)
            "337, Platon, 123, 200, false, 1",   // korisnik sa administratorskim pravima lajkuje smestaj(LK)
            "337, '', '', 302, true, 1",       // strano lice anlajkuje smestaj(NK)
            "337, '', '', 302, false, 1"         // strano lice lajkuje smestaj(NK)
    })
    void lajkovanjeSmestaja(int idSmestaja, String korisnickoIme,
                            String sifra, int statusniKod,
                            boolean lajkovan, int brojLajkova) throws Exception
    {

        if(korisnickoIme.length() > 0) {
            MvcResult auth = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .param("username", korisnickoIme)
                    .param("password", sifra)).andReturn();

            MockHttpSession sesija = (MockHttpSession) auth.getRequest().getSession();

            this.mockMvc.perform(MockMvcRequestBuilders.post((lajkovan ? "/unlike/" : "/like/") + idSmestaja).session(sesija))
                    .andExpect(status().is(statusniKod));

            assertEquals(lajkovan? (brojLajkova - 1) : (brojLajkova + 1), smestajDAO.get(idSmestaja).get().getBrojLajkova());
        } else {
            this.mockMvc.perform(MockMvcRequestBuilders.post((lajkovan ? "/unlike/" : "/like/") + idSmestaja))
                    .andExpect(status().is(statusniKod));

            assertEquals(brojLajkova, smestajDAO.get(idSmestaja).get().getBrojLajkova());
        }

    }
}