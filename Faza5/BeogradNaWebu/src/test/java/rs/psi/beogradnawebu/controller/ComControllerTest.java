package rs.psi.beogradnawebu.controller;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runners.model.MemberValueConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.expression.Mvc;
import rs.psi.beogradnawebu.dao.KomentarDAO;
import rs.psi.beogradnawebu.model.Komentar;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ComControllerTest {

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
    private KomentarDAO komentarDAO;

    @ParameterizedTest
    @CsvSource({
            "'komentar', marko, marko, 200, true", // korisnik bez administratorskih prava pokusava da objavi komentar(LK), komentar sadrzi najmanje 1 karakter(LK)
            "'komentar', Platon, 123, 200, true",  // korisnik sa administratorskim pravima pokusava da objavi komentar(LK), komentar sadrzi najmanje 1 karakter(LK)
            "' ', marko, marko, 302, false",       // korisnik bez administratorskih prava pokusava da objavi komentar(LK), komentar je duzine 0(NK)
                                                   // !!! komentar se objavljuje, iako u tekstu komentara ne postoji barem 1 karakter
            "'komentar', '', '', 302, false"       // strano lice (gost ili zahtevi van sajta) pokusava da objavi komentar(NK), komentar sadrzi najmanje 1 karakter(LK)
                                                   // !!! komentar se ne objavljuje, ali je povratni kod 200, ne redirekt na login
    })
    void addKomentar(String tekstKomentara, String korisnickoIme, String sifra, int statusniKod, boolean unetKomentar) throws Exception {

        if(korisnickoIme.length() > 0) {
            MvcResult auth = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .param("username", korisnickoIme)
                    .param("password", sifra)).andReturn();

            MockHttpSession sesija = (MockHttpSession) auth.getRequest().getSession();

            this.mockMvc.perform(MockMvcRequestBuilders.post("/noviKomentar/500/" + tekstKomentara).session(sesija))
                    .andExpect(status().is(statusniKod));
        } else {
            this.mockMvc.perform(MockMvcRequestBuilders.post("/noviKomentar/500/" + tekstKomentara))
                    .andExpect(status().is(statusniKod));
        }

        assertEquals(unetKomentar ? 1 : 0, komentarDAO.allKomentar(500).size()); // smestaj sa id 500 nema na pocetku komentare

    }

    @ParameterizedTest
    @CsvSource({
            "500, true",  // sistem dohvata listu svih komentara koja je prazna za dati smestaj(LK)
            "337, false"  // sistem dohvata listu svih komentara koja nije prazna za dati smestaj(LK)
    })
    void allKomentar(int idSmestaja, boolean praznaLista) throws Exception {

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/sviKomentari/" + idSmestaja))
                .andExpect(status().is(200))
                .andReturn();

        if(praznaLista)
            assertEquals("[]", result.getResponse().getContentAsString());
        else {
            List<Komentar> listaKomentaraLokalno = komentarDAO.allKomentar(idSmestaja);
            JSONArray listaKomentaraVraceno = new JSONArray(result.getResponse().getContentAsString());
            assertEquals(listaKomentaraLokalno.size(), listaKomentaraVraceno.length());

            for(int i = 0; i < listaKomentaraVraceno.length(); i++) {
                int idSmestajaVraceno = listaKomentaraVraceno.getJSONObject(i).getInt("idsmestaj");
                String komentarVraceno = listaKomentaraVraceno.getJSONObject(i).getString("tekstKomentara");
                int brojLajkovaVraceno = listaKomentaraVraceno.getJSONObject(i).getInt("broj_lajkova");

                boolean pronadjen = false;
                for(Komentar komentarLokalno: listaKomentaraLokalno) {
                    if(komentarLokalno.getBroj_lajkova() == brojLajkovaVraceno &&
                       komentarLokalno.getTekstKomentara().equals(komentarVraceno) &&
                       komentarLokalno.getIdsmestaj() == idSmestajaVraceno)
                    {
                        pronadjen = true;
                        break;
                    }
                }
                assertEquals(true, pronadjen);
            }
        }
    }

    // sistem dohvata maksimalni id(LK)
    @Test
    @WithMockUser(username = "marko",roles = {"KORISNIK"})
    void maxID() throws Exception {

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/maxID"))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals(Integer.parseInt(result.getResponse().getContentAsString()), 99);
    }

    @ParameterizedTest
    @CsvSource({
            "80, -1, marko, marko, 200",  // korisnik bez administratorskih prava lajkuje komentar koji je vec lajkovao(LK)
            "83, 1, marko, marko, 200",   // korisnik bez administratorskih prava lajkuje komentar koji nije lajkovao(LK)
            "83, -1, Platon, 123, 200",   // korisnik sa administratorskim pravima lajkuje komentar koji je vec lajkovao(LK)
            "91, 1, Platon, 123, 200",    // korisnik sa administratorskim pravima lajkuje komentar koji nije lajkovao(LK)
            "80, 1, '', '', 302",         // strano lice lajkuje komentar(NK)
            "80, -1, '', '', 302"         // strano lice anlajkuje komentar(NK)
    })
    void lajkovanjeKomentar(int idKomentara, int delta, String korisnickoIme, String sifra, int statusniKod) throws Exception {

        MockHttpSession sesija = null;
        if(korisnickoIme.length() > 0) {
            MvcResult auth = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .param("username", korisnickoIme)
                    .param("password", sifra))
                    .andReturn();

            sesija = (MockHttpSession) auth.getRequest().getSession();
        }

        Komentar komentar = komentarDAO.get(idKomentara).get();
        long brojLajkovaPre = komentar.getBroj_lajkova();

        if(sesija != null)
            this.mockMvc.perform(MockMvcRequestBuilders.post((delta == -1 ? "/unlikeKomentar/" : "/komentarLike/") + idKomentara).session(sesija))
                    .andExpect(status().is(statusniKod));
        else
            this.mockMvc.perform(MockMvcRequestBuilders.post((delta == -1 ? "/unlikeKomentar/" : "/komentarLike/") + idKomentara))
                    .andExpect(status().is(statusniKod));

        komentar = komentarDAO.get(idKomentara).get();
        long brojLajkovaPosle = komentar.getBroj_lajkova();

        assertEquals(statusniKod == 200 ? brojLajkovaPre + delta : brojLajkovaPre, brojLajkovaPosle);

    }

    @ParameterizedTest
    @CsvSource({
            "80, true",  // sistem dohvata komentar koji trenutni korisnik jeste lajkovao(LK)
            "83, false" // sistem dohvata komentar koji trenutni korisnik nije lajkovao(LK)
    })
    @WithMockUser(username = "marko",roles = {"KORISNIK"})
    void isLiked(int idKomentara, boolean lajkovanKomentar) throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/islikedKomentar/marko/" + idKomentara))
                                        .andExpect(status().is(200))
                                        .andReturn();

        assertEquals(lajkovanKomentar, Boolean.parseBoolean(result.getResponse().getContentAsString()));

    }

    @ParameterizedTest
    @CsvSource({
            "99, marko, marko, 200",  // korisnik bez administratorskih privilegija brise svoj komentar(LK)
            "80, Platon, 123, 200",   // korisnik sa administratorskim privilegijama brise svoj komentar(LK)
            "99, Platon, 123, 200",   // korisnik sa administratorskim privilegijama brise tudji komentar(LK)
            "80, marko, marko, 403",  // korisnik bez administratorskih privilegija brise tudji komentar(NK)
                                      // !!! Pogresan povratni kod (500), komentar se ne brise.
            "99, '', '', 302"         // strano lice pokusava da obrise komentar (NK)
                                      // !!! Pogresan povratni kod (200), komentar se ne brise.
    })
    void brisanjeKomentara(int idKomentara, String korisnickoIme, String sifra, int statusniKod) throws Exception {

        Komentar komentar = komentarDAO.get(idKomentara).get();

        MockHttpSession sesija = null;
        if(korisnickoIme.length() > 0) {
            MvcResult auth = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .param("username", korisnickoIme)
                    .param("password", sifra))
                    .andReturn();

            sesija = (MockHttpSession) auth.getRequest().getSession();

            this.mockMvc.perform(MockMvcRequestBuilders.post("/obrisiKomentar/" + idKomentara).session(sesija))
                    .andExpect(status().is(statusniKod));

            Optional<Komentar> obrisanKomentar = komentarDAO.get(idKomentara);
            assertEquals(statusniKod == 200 ? true : false, obrisanKomentar == null || obrisanKomentar.isEmpty());
                                                                  // u slucaju exception-a DAO vraca null umesto Optional !!!
        } else {
            this.mockMvc.perform(MockMvcRequestBuilders.post("/obrisiKomentar/" + idKomentara))
                    .andExpect(status().is(statusniKod));

            Optional<Komentar> obrisanKomentar = komentarDAO.get(idKomentara);
            assertEquals(false, obrisanKomentar.isEmpty());
        }

    }
}
