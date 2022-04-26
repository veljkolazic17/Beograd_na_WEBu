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
public class LajkSmestajaDAO implements DAO<LajkSmestaja> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    private RowMapper<LajkSmestaja> rowMapper = (rs, rowNum) -> {
        LajkSmestaja lajkSmestaja = new LajkSmestaja();
        lajkSmestaja.setIdkorisnik(rs.getLong("idkorisnik"));
        lajkSmestaja.setIdsmestaj(rs.getLong("idsmestaj"));
        return lajkSmestaja;
    };

    public LajkSmestajaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LajkSmestaja> list() {
        return null;
    }

    @Override
    public void create(LajkSmestaja lajkSmestaja) {

    }

    @Override
    public Optional<LajkSmestaja> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(LajkSmestaja lajkSmestaja, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
