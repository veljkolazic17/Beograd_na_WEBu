package rs.psi.beogradnawebu.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.model.TipSmestaja;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TipSmestajaDAOTest {

    @Autowired
    private TipSmestajaDAO tipSmestajaDAO;

    @Test
    void list() {
        List<TipSmestaja> tipSmestajas = tipSmestajaDAO.list();
        assertEquals(2,tipSmestajas.size());
    }

    @Test
    void create() {
        TipSmestaja tipSmestaja = new TipSmestaja();
        tipSmestaja.setImeTipa("Bela Crkva");

        tipSmestajaDAO.create(tipSmestaja);
        List<TipSmestaja> tipSmestajas = tipSmestajaDAO.list();
        assertEquals(3,tipSmestajas.size());
    }

    @Test
    void get_VanOpsega() {
        Optional<TipSmestaja> tipSmestaja = tipSmestajaDAO.get(66);
        assertNull(tipSmestaja);
    }

    @Test
    void get_UOpsegu(){
        Optional<TipSmestaja> tipSmestaja = tipSmestajaDAO.get(1);
        assertNotNull(tipSmestaja);
    }

    @Test
    void update() {
        TipSmestaja tipSmestaja = new TipSmestaja();
        tipSmestaja.setImeTipa("Bela Crkva");
        tipSmestajaDAO.update(tipSmestaja,1);

        Optional<TipSmestaja> tipSmestaja1 = tipSmestajaDAO.get(1);
        assertEquals("Bela Crkva",tipSmestaja1.orElse(null).getImeTipa());
    }

    @Test
    void delete() {
        TipSmestaja tipSmestaja = new TipSmestaja();
        tipSmestaja.setImeTipa("Bela Crkva");
        tipSmestajaDAO.create(tipSmestaja);

        List<TipSmestaja> tipSmestajaList = tipSmestajaDAO.list();
        int dummyID = (int) tipSmestajaList.get(tipSmestajaList.size()-1).getIdtipSmestaja();


        assertNotNull(tipSmestajaDAO.get(dummyID).orElse(null));
        tipSmestajaDAO.delete(dummyID);
        assertNull(tipSmestajaDAO.get(dummyID));
    }
}