package rs.psi.beogradnawebu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.services.MailService;
import rs.psi.beogradnawebu.services.SimplePasswordGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
class UserDataControllerTest {


    @MockBean
    private KorisnikDAO korisnikDAO;

    @MockBean
    private SimplePasswordGenerator simplePasswordGenerator;

    @MockBean
    private MailService mailService;

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

    @Test
    @WithMockUser(username = "MAT", password = "123", roles = {"KORISNIK"})
    void resetData() throws Exception {

        Korisnik mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnickoime("MAT");
        Optional<Korisnik> mockOptionalKorisnik = Optional.of(mockKorisnik);
        when(korisnikDAO.getUserByUsername(anyString())).thenReturn(mockOptionalKorisnik);

        ResultActions auth =this.mockMvc.perform(post("/login")
                .param("username", "MAT")
                .param("password","123")
        );
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();

        mockMvc.perform(post("/userdata").session(session))
                .andExpect(status().is(202));

        assertEquals(session.getAttribute("myrec"),null);

    }

    @Test
    @WithMockUser(username = "MAT", password = "123", roles = {"KORISNIK"})
    void passwordResetViaEmail() throws Exception {

        Korisnik mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnickoime("MAT");
        Optional<Korisnik> mockOptionalKorisnik = Optional.of(mockKorisnik);
        when(korisnikDAO.getUserByUsername(anyString())).thenReturn(mockOptionalKorisnik);

        when(simplePasswordGenerator.generatePassword()).thenReturn("novasifra");

        mockMvc.perform(post("/userdata/passwordresetvialemail/MAT"))
                .andExpect(status().is(200));

        assertEquals(mockKorisnik.getSifra(),"novasifra");
    }

    @Test
    @WithMockUser(username = "MAT", password = "123", roles = {"ADMIN"})
    void obrisiKorisnika_BrisanjeSamogSebe() throws Exception {
        Korisnik mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnickoime("MAT");
        mockKorisnik.setUloga(1);
        Optional<Korisnik> mockOptionalKorisnik = Optional.of(mockKorisnik);
        when(korisnikDAO.getUserByUsername(anyString())).thenReturn(mockOptionalKorisnik);

        mockMvc.perform(post("/userdata/obrisiKorisnika/MAT"))
                .andExpect(status().is(501));
    }

    @Test
    @WithMockUser(username = "MAT", password = "123", roles = {"ADMIN"})
    void obrisiKorisnika() throws Exception {
        Korisnik mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnickoime("marko");
        Optional<Korisnik> mockOptionalKorisnik = Optional.of(mockKorisnik);
        when(korisnikDAO.getUserByUsername(anyString())).thenReturn(mockOptionalKorisnik);
        doNothing().when(korisnikDAO).delete(anyInt());

        mockMvc.perform(post("/userdata/obrisiKorisnika/marko"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(username = "MAT", password = "123", roles = {"KORISNIK"})
    void obrisiKorisnika_ZabranjenPristup() throws Exception {
        Korisnik mockKorisnik = new Korisnik();
        mockKorisnik.setKorisnickoime("Boban");
        Optional<Korisnik> mockOptionalKorisnik = Optional.of(mockKorisnik);
        when(korisnikDAO.getUserByUsername(anyString())).thenReturn(mockOptionalKorisnik);
        doNothing().when(korisnikDAO).delete(anyInt());

        mockMvc.perform(post("/userdata/obrisiKorisnika/Platon"))
                .andExpect(status().is(403));
    }

}