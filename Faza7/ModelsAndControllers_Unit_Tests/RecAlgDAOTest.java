package rs.psi.beogradnawebu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import rs.psi.beogradnawebu.model.Recalgdata;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RecAlgDAOTest {

    @Autowired
    RecAlgDAO recAlgDAO;

    @Test
    void list() {
        List<Recalgdata> list = recAlgDAO.list();
        assertEquals(3,list.size());
    }

    @Test
    void create() {
        Recalgdata recalgdata = new Recalgdata();
        recalgdata.setIdkorisnik(25);
        recAlgDAO.create(recalgdata);
        List<Recalgdata> list = recAlgDAO.list();
        assertEquals(4,list.size());
    }

    @Test
    void getIsNull() {
        Recalgdata recalgdata = recAlgDAO.get(1000).orElse(null);
        assertNull(recalgdata);
    }

    @Test
    void getIsNotNull() {
        Recalgdata recalgdata = recAlgDAO.get(56).orElse(null);
        assertNotNull(recalgdata);
    }

    @Test
    void update() {
        Recalgdata recalgdata = recAlgDAO.get(56).orElse(null);
        assert recalgdata != null;
        recalgdata.setWeigthSpratonst(0.677);
        recAlgDAO.update(recalgdata,56);
        Recalgdata updated = recAlgDAO.get(56).orElse(null);
        assert updated != null;
        assertEquals(0.677,updated.getWeigthSpratonst());
    }

    @Test
    void delete() {
        recAlgDAO.delete(56);
        List<Recalgdata> list = recAlgDAO.list();
        assertEquals(2,list.size());

    }
}