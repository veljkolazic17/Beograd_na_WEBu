package rs.psi.beogradnawebu.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.model.TipSmestaja;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class LajkSmestajaCDAOTest {

    @Autowired
    private LajkSmestajaCDAO lajkSmestajaCDAO;

    @Test
    void list() {
        List<LajkSmestaja> lajkSmestajaList = lajkSmestajaCDAO.list();
        assertEquals(8,lajkSmestajaList.size());
    }

    @Test
    void create() {
        LajkSmestaja lajkSmestaja = new LajkSmestaja();
        lajkSmestaja.setIdkorisnik(25);
        lajkSmestaja.setIdsmestaj(337);

        lajkSmestajaCDAO.create(lajkSmestaja);

        List<LajkSmestaja> lajkSmestajaList = lajkSmestajaCDAO.list();
        assertEquals(9,lajkSmestajaList.size());
    }

    @Test
    void get_VanOpsega() {
        Optional<LajkSmestaja> lajkSmestaja = lajkSmestajaCDAO.get(new int[]{1000,1000});
        assertNull(lajkSmestaja);
    }

    @Test
    void get_UOpsegu() {
        Optional<LajkSmestaja> lajkSmestaja = lajkSmestajaCDAO.get(new int[]{56,337});
        assertNotNull(lajkSmestaja);
    }

    @Test
    void update() {
        LajkSmestaja lajkSmestaja = new LajkSmestaja();
        lajkSmestaja.setIdsmestaj(340);
        lajkSmestaja.setIdkorisnik(56);

        lajkSmestajaCDAO.update(lajkSmestaja,new int[]{56,337});

        Optional<LajkSmestaja> lajkSmestajaOptional = lajkSmestajaCDAO.get(new int[]{56,337});
        assertNotNull(lajkSmestajaOptional);
    }

    @Test
    void delete() {
        lajkSmestajaCDAO.delete(new int[]{56,337});

        Optional<LajkSmestaja> lajkSmestajaOptional = lajkSmestajaCDAO.get(new int[]{56,337});
        assertNotNull(lajkSmestajaOptional);
    }

    @Test
    void deleteLikes() {
        lajkSmestajaCDAO.deleteLikes(56);
        lajkSmestajaCDAO.deleteLikes(57);

        List<LajkSmestaja> lajkSmestajas = lajkSmestajaCDAO.list();

        for(LajkSmestaja lajkSmestaja : lajkSmestajas){
            assertNotEquals(56,lajkSmestaja.getIdkorisnik());
            assertNotEquals(57,lajkSmestaja.getIdkorisnik());
        }
    }

    @Test
    void getLast() {
        Optional<Smestaj> smestaj56 = lajkSmestajaCDAO.getLast(56);
        Optional<Smestaj> smestaj57 = lajkSmestajaCDAO.getLast(57);

        assertEquals(339,smestaj56.orElse(null).getIdsmestaj());
        assertEquals(345,smestaj57.orElse(null).getIdsmestaj());
    }

    @Test
    void getLikes() {
        List<LajkSmestaja> lajkSmestajas56 = lajkSmestajaCDAO.getLikes(56).orElse(null);
        List<LajkSmestaja> lajkSmestajas57 = lajkSmestajaCDAO.getLikes(57).orElse(null);

        assertEquals(3,lajkSmestajas56.size());
        assertEquals(2,lajkSmestajas57.size());
    }
}