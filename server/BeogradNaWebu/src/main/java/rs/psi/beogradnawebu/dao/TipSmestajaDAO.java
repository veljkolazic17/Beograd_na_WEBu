package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private RowMapper<TipSmestaja> rowMapper = (rs, rowNum) -> {
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
        return null;
    }

    @Override
    public void create(TipSmestaja tipSmestaja) {

    }

    @Override
    public Optional<TipSmestaja> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(TipSmestaja tipSmestaja, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
