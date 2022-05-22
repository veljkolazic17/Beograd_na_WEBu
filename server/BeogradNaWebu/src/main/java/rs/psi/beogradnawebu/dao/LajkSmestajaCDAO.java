package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.List;
import java.util.Optional;

@Component
public class LajkSmestajaCDAO implements CDAO<LajkSmestaja> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;
    private SmestajDAO smestajDAO;

    public static RowMapper<LajkSmestaja> rowMapper = (rs, rowNum) -> {
        LajkSmestaja lajkSmestaja = new LajkSmestaja();
        lajkSmestaja.setIdkorisnik(rs.getLong("idkorisnik"));
        lajkSmestaja.setIdsmestaj(rs.getLong("idsmestaj"));
        return lajkSmestaja;
    };

    public LajkSmestajaCDAO(JdbcTemplate jdbcTemplate, SmestajDAO smestajDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.smestajDAO = smestajDAO;
    }

    @Override
    public List<LajkSmestaja> list() {
        List<LajkSmestaja> result = jdbcTemplate.query("select * from lajk_smestaja", rowMapper);
        return result;
    }

    @Override
    public void create(LajkSmestaja lajkSmestaja) {
        jdbcTemplate.update("insert into lajk_smestaja (idkorisnik,idsmestaj) values (?,?)", lajkSmestaja.getIdkorisnik(), lajkSmestaja.getIdsmestaj());

    }

    @Override
    public Optional<LajkSmestaja> get(int[] id) {
        LajkSmestaja lajkSmestaja = null;
        try {
            lajkSmestaja = jdbcTemplate.queryForObject("select * from lajk_smestaja where idkorisnik = ? and idsmestaj = ?", rowMapper, id[0], id[1]);
        } catch (Exception exception) {
            log.info("LajkSmestaja nije pronadjen: " + id[0] + " " + id[1]);
        }
        return Optional.ofNullable(lajkSmestaja);
    }

    @Override
    public void update(LajkSmestaja lajkSmestaja, int[] id) {
        try {
            jdbcTemplate.update("update lajk_smestaja set idkorisnik = ?, idsmestaj = ? where idkorisnik = ? and idsmestaj = ?",
                    lajkSmestaja.getIdkorisnik(), lajkSmestaja.getIdsmestaj(), id[0], id[1]);
        } catch (Exception exception) {
            log.info("Nije moguce promeniti lajksmestaja");
        }
    }

    @Override
    public void delete(int[] id) {
        try {
            //CHANGED
            jdbcTemplate.update("delete from lajk_smestaja where idkorisnik = ? and idsmestaj = ?", id[0], id[1]);
        } catch (Exception e) {
            log.info("Nije moguce izbrisati lajksmestaja");
        }
    }

    public Optional<Smestaj> getLast(int idkorisnik) {
        //int idkorisnik = (int)korisnik.getIdkorisnik();
        //PROVERITI DA LI KORISNIK POSTOJI !!!!
        List<LajkSmestaja> smestajList = jdbcTemplate.query("select * from lajk_smestaja where idkorisnik = ?", rowMapper, idkorisnik);
        if (smestajList.size() == 0)
            return Optional.empty();
        LajkSmestaja lajkSmestaja = smestajList.get(smestajList.size() - 1);
        return smestajDAO.get((int) (lajkSmestaja.getIdsmestaj()));
    }

    public Optional<List<LajkSmestaja>> getLikes(int idkorisnik) {
        List<LajkSmestaja> likes = jdbcTemplate.query("SELECT * FROM lajk_smestaja WHERE idkorisnik = ?", rowMapper, idkorisnik);
        return Optional.of(likes);
    }
}
