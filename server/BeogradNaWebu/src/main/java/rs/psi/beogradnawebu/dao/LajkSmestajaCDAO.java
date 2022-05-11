package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.LajkSmestaja;

import java.util.List;
import java.util.Optional;

@Component
public class LajkSmestajaCDAO implements CDAO<LajkSmestaja> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    public static RowMapper<LajkSmestaja> rowMapper = (rs, rowNum) -> {
        LajkSmestaja lajkSmestaja = new LajkSmestaja();
        lajkSmestaja.setIdkorisnik(rs.getLong("idkorisnik"));
        lajkSmestaja.setIdsmestaj(rs.getLong("idsmestaj"));
        return lajkSmestaja;
    };

    public LajkSmestajaCDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LajkSmestaja> list() {
        List<LajkSmestaja> result = jdbcTemplate.query("select * from lajk_smestaja",rowMapper);
        return result;
    }

    @Override
    public void create(LajkSmestaja lajkSmestaja) {
        jdbcTemplate.update("insert into lajk_smestaja (idkorisnik,idsmestaj) values (?,?)",lajkSmestaja.getIdkorisnik(),lajkSmestaja.getIdsmestaj());

    }

    @Override
    public Optional<LajkSmestaja> get(int[] id) {
        LajkSmestaja lajkSmestaja = null;
        try{
            lajkSmestaja = jdbcTemplate.queryForObject("select * from lajk_smestaja where idkorisnik = ? and idsmestaj = ?",rowMapper,id[0],id[1]);
        }catch(Exception exception){
            log.info("LajkSmestaja nije pronadjen: " + id[0] + " " + id[1]);
        }
        return Optional.ofNullable(lajkSmestaja);
    }

    @Override
    public void update(LajkSmestaja lajkSmestaja, int[] id) {
        try {
            jdbcTemplate.update("update lajk_smestaja set idkorisnik = ?, idsmestaj = ? where idkorisnik = ? and idsmestaj = ?",
                    lajkSmestaja.getIdkorisnik(),lajkSmestaja.getIdsmestaj(),id[0],id[1]);
        } catch (Exception exception){
            log.info("Nije moguce promeniti lajksmestaja");
        }
    }

    @Override
    public void delete(int[] id) {
        try {
            jdbcTemplate.update("delete from lajk_smestaja where idkorisnik = ? and idsmestaj = ?,id[0],id[1]");
        } catch (Exception e){
            log.info("Nije moguce izbrisati lajksmestaja");
        }
    }
}
