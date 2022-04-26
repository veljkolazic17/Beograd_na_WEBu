package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.LajkKomentara;

import java.util.List;
import java.util.Optional;

@Component
public class LajkKomentaraDAO implements DAO<LajkKomentara> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    private RowMapper<LajkKomentara> rowMapper = (rs, rowNum) -> {
        LajkKomentara lajkKomentara = new LajkKomentara();
        lajkKomentara.setIdkomentar(rs.getLong("idkomentar"));
        lajkKomentara.setIdkorisnik(rs.getLong("idkorisnik"));
        return lajkKomentara;
    };

    public LajkKomentaraDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LajkKomentara> list() {
        return null;
    }

    @Override
    public void create(LajkKomentara lajkKomentara) {

    }

    @Override
    public Optional<LajkKomentara> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(LajkKomentara lajkKomentara, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
