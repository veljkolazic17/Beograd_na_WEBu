package rs.psi.beogradnawebu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.dto.FilterDTO;

import org.hamcrest.Matchers;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.CharMatcher.isNot;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
class FilterControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    SmestajDAO smestajDAO;

    @Autowired
    LajkSmestajaCDAO lajkSmestajaCDAO;

    @Autowired
    KorisnikDAO korisnikDAO;

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "MAT",roles = {"KORISNIK"})
    void filterSmestaj_filterDataNotNull()throws Exception{
        ResultActions auth =this.mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "MAT")
                .param("password","123"));
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
        mockMvc.perform(
                post("/pregledsmestaja/filter")
                        .param("Lokacija","Zvezdara")
                        .session(session)
        )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"))
                .andExpect(flash().attribute("filterf",notNullValue()));

        assertEquals(session.getAttribute("displayflag"),1);
    }

    @Test
    @WithMockUser(username = "MAT",roles = {"KORISNIK"})
    void filterSmestaj_filterDataNullandFilterAttributeNull()throws Exception{
        ResultActions auth =this.mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "MAT")
                .param("password","123"));
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
        session.setAttribute("myfilter",null);
        mockMvc.perform(
                post("/pregledsmestaja/filter")
                        .session(session)
        )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"));
        assertNull(session.getAttribute("displayflag"));
    }

    @Test
    @WithMockUser(username = "MAT",roles = {"KORISNIK"})
    void filterSmestaj_filterDataNullandFilterAttributeNotNull()throws Exception{
        ResultActions auth =this.mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "MAT")
                .param("password","123"));
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
        FilterDTO myFilter = new FilterDTO();
        myFilter.setLokacija("TestLokacija");
        session.setAttribute("myfilter",myFilter);
        mockMvc.perform(
                post("/pregledsmestaja/filter")
                        .session(session)
        )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/pregledsmestaja/0"));

        assertEquals(session.getAttribute("displayflag"),1);
        assertEquals(((FilterDTO)session.getAttribute("myfilter")).getLokacija(),"TestLokacija");
    }

    @Test
    @WithMockUser(username = "MAT",roles = {"KORISNIK"})
    void listSmestaj_displayFlagNull()throws Exception{
        ResultActions auth =this.mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "MAT")
                .param("password","123"));
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
        session.setAttribute("displayflag",null);
        mockMvc.perform(
                get("/pregledsmestaja/4")
                        .session(session)
        )
                .andExpect(status().is(200))
                .andExpect(view().name("glavnaStranicaKorisnik"))
                .andExpect(model().attribute("lastPage",false))
                .andExpect(model().attribute("smestajList",notNullValue()));
    }
    @Test
    @WithMockUser(username = "MAT",roles = {"KORISNIK"})
    void listSmestaj_displayFlagNotNull_andIsNotReccomend() throws Exception{
        ResultActions auth =this.mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "MAT")
                .param("password","123"));
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
        session.setAttribute("displayflag",1);
        session.setAttribute("myfilter",new FilterDTO());
        mockMvc.perform(
                get("/pregledsmestaja/3")
                        .session(session)
        )
                .andExpect(status().is(200))
                .andExpect(view().name("glavnaStranicaKorisnik"))
                .andExpect(model().attribute("smestajList",notNullValue()));
    }
    @Test
    @WithMockUser(username = "MAT",roles = {"KORISNIK"})
    void listSmestaj_displayFlagNotNull_andIsReccomend() throws Exception {
        ResultActions auth = this.mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "MAT")
                .param("password", "123"));
        MvcResult result = auth.andReturn();
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
        session.setAttribute("displayflag",2);
        List<Smestaj> list = new ArrayList<>();
        Smestaj s= new Smestaj();
        s.setLokacija("TestLokacija");
        list.add(s);
        session.setAttribute("myrec",list);
        mockMvc.perform(
                get("/pregledsmestaja/5")
                        .session(session)
        )
                .andExpect(status().is(200))
                .andExpect(view().name("glavnaStranicaKorisnik"))
                .andExpect(model().attribute("lastPage",true))
                .andExpect(model().attribute("smestajList",notNullValue()));

    }
    @Test
    void listSmestaj_Guest_noFilter()throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("myfilter",null);
        mockMvc.perform(
                get("/pregledsmestaja/4")
                        .session(session)
        )
                .andExpect(status().is(200))
                .andExpect(view().name("glavnaStranicaGost"))
                .andExpect(model().attribute("smestajList",notNullValue()));
    }
    @Test
    void listSmestaj_Guest_withWrongFilter()throws Exception{
        MockHttpSession session = new MockHttpSession();
        FilterDTO filter = new FilterDTO();
        filter.setLokacija("Zvzdara");
        session.setAttribute("myfilter",filter);
        mockMvc.perform(
                get("/pregledsmestaja/0")
                        .session(session)
        )
                .andExpect(status().is(200))
                .andExpect(view().name("glavnaStranicaGost"))
                .andExpect(model().attribute("lastPage",true));
    }
    @Test
    void listSmestaj_Guest_withRightFilter()throws Exception{
        MockHttpSession session = new MockHttpSession();
        FilterDTO filter = new FilterDTO();
        filter.setLokacija("Zvezdara");
        session.setAttribute("myfilter",filter);
        mockMvc.perform(
                        get("/pregledsmestaja/0")
                                .session(session)
                )
                .andExpect(status().is(200))
                .andExpect(view().name("glavnaStranicaGost"))
                .andExpect(model().attribute("lastPage",false))
                .andExpect(model().attribute("smestajList",notNullValue()));
    }

}