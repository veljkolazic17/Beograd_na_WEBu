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
import rs.psi.beogradnawebu.dto.FilterDTO;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SmestajDAOTest {

    @Autowired
    SmestajDAO smestajDAO;

    @Test
    void list() throws Exception{
        List<Smestaj> list = smestajDAO.list();
        assertEquals(142,list.size());
    }

    @Test
    void create() throws Exception{
        Smestaj smestaj = new Smestaj();
        smestaj.setIdtipSmestaja(1);
        smestaj.setOrgPutanja("putanja");
        smestaj.setPostoji(1);
        smestaj.setBrojSajta(1);
        smestajDAO.create(smestaj);
        List<Smestaj> list = smestajDAO.list();
        assertEquals(143,list.size());
    }

    @Test
    void getIsNull() throws Exception{
        Smestaj smestaj = smestajDAO.get(10000).orElse(null);
        assertNull(smestaj);
    }

    @Test
    void getIsNotNull() throws Exception{
        Smestaj smestaj = smestajDAO.get(400).orElse(null);
        assertNotNull(smestaj);
    }

    @Test
    void update() throws Exception{
        Smestaj smestaj = smestajDAO.get(400).orElse(null);
        assert smestaj != null;
        smestaj.setLokacija("TestLokacija");
        smestajDAO.update(smestaj,400);
        Smestaj res = smestajDAO.get(400).orElse(null);
        assert res != null;
        assertEquals("TestLokacija",res.getLokacija());
    }

    @Test
    void decLikes() throws Exception{
        Smestaj test = smestajDAO.get(337).orElse(null);
        assert test != null;
        long likes = test.getBrojLajkova();
        smestajDAO.decLikes(56);
        Smestaj res = smestajDAO.get(337).orElse(null);
        assert res != null;
        long newLikes = res.getBrojLajkova();
        assertEquals(newLikes,likes - 1);

    }

    @Test
    void delete() throws Exception{
        smestajDAO.delete(337);
        Smestaj smestaj = smestajDAO.get(337).orElse(null);
        assertNull(smestaj);
    }

    @Test
    void getByOffset() throws Exception{
        List<Smestaj> result = smestajDAO.getByOffset(2,16);
        assertEquals(16,result.size());
    }

    @Test
    void searchByFilters() throws Exception{
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setLokacija("Beograd na vodi");
        List<Smestaj> result = smestajDAO.searchByFilters(filterDTO,0);
        assertEquals(8,result.size());
    }

    @Test
    void searchByFiltersPriceRange() throws Exception{
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setCenaOd(50);
        filterDTO.setCenaDo(500);
        List<Smestaj> result = smestajDAO.searchByFilters(filterDTO,0);
        assertEquals(10,result.size());
    }

    @Test
    void checkIfExist() throws Exception{
        boolean res = smestajDAO.checkIfExist("https://www.4zida.rs/izdavanje/stanovi/beograd/oglas/klare-cetkin/62681fe4a9095362090c9ef6");
        assertTrue(res);
        Smestaj smestaj = smestajDAO.get(342).orElse(null);
        assert smestaj != null;
        long flag = smestaj.getPostoji();
        assertEquals(1,flag);
    }

    @Test
    void deleteWithFalseTag() throws Exception{
        List<Smestaj> beforeDelete = smestajDAO.list();
        smestajDAO.deleteWithFalseTag(2);
        List<Smestaj> afterDelete = smestajDAO.list();
        assertEquals(beforeDelete.size() - 61,afterDelete.size());
    }

    @Test
    void setAllTags() throws Exception{
        smestajDAO.setAllTags(2);
        smestajDAO.deleteWithFalseTag(2);
        List<Smestaj> list = smestajDAO.list();
        assertEquals(59,list.size());
    }

    @Test
    void getAvgAcc() throws Exception{
        SmestajDAO.AvgData averageAcc = smestajDAO.getAvgAcc(56);
        assertEquals(1206.6666666666667,averageAcc.cena);
    }

    @Test
    void mapBrojSoba() throws Exception{
        double res = smestajDAO.mapBrojSoba("Garsonjera");
        assertEquals(0.5,res);
    }
}