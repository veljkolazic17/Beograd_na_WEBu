package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.TipSmestaja;

import java.util.List;
import java.util.Optional;

@Component
public class TipSmestajaDAO implements DAO<TipSmestaja> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    public static RowMapper<TipSmestaja> rowMapper = (rs, rowNum) -> {
        TipSmestaja tipSmestaja = new TipSmestaja();
        tipSmestaja.setIdtipSmestaja(rs.getLong("idtip_smestaja"));
        tipSmestaja.setImeTipa(rs.getString("ime_tipa"));
        return tipSmestaja;
    };

    public TipSmestajaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TipSmestaja> list() {

        return jdbcTemplate.query("SELECT * FROM tip_smestaja",rowMapper);
    }

    @Override
    public void create(TipSmestaja tipSmestaja) {
        try {
            jdbcTemplate.update("INSERT INTO tip_smestaja (ime_tipa) VALUES (?)",tipSmestaja.getImeTipa());
        }
        catch (Exception e){
            log.info("Neispravan tip smestaja");
        }

    }

    @Override
    public Optional<TipSmestaja> get(int id) {
        Optional<TipSmestaja> tip = null;
        try {
            TipSmestaja tipSmestaja =jdbcTemplate.queryForObject("SELECT * FROM tip_smestaja WHERE idtip_smestaja = ?", rowMapper, id);
            tip = Optional.ofNullable(tipSmestaja);
        }
        catch (Exception e){
            log.info("Nije pronadjen tip smestaja sa ID: " + id);
        }
        return tip;

    }

    @Override
    public void update(TipSmestaja tipSmestaja, int id) {
        try {
            jdbcTemplate.update("UPDATE tip_smestaja SET ime_tipa = ? WHERE idtip_smestaja = ?",tipSmestaja.getImeTipa(),id);

        }
        catch (Exception e){
            log.info("Nije pronadjen tip smestaja sa ID: " + id);
        }

    }

    @Override
    public void delete(int id) {
        try {
            jdbcTemplate.update("DELETE FROM tip_smestaja WHERE idtip_smestaja = ?",id);

        }
        catch (Exception e){
            log.info("Nije pronadjen tip smestaja sa ID: " + id);
        }
    }
}
