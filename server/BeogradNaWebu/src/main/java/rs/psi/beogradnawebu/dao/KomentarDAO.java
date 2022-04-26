package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.Komentar;

import java.util.List;
import java.util.Optional;
@Component
public class KomentarDAO implements DAO<Komentar> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;


    private RowMapper<Komentar> rowMapper = (rs, rowNum) -> {
        Komentar komentar = new Komentar();
        komentar.setIdkomentar(rs.getLong("idkomentar"));
        komentar.setTekstKomentara(rs.getString("tekst_komentara"));
        komentar.setIdkorisnik(rs.getInt("idkorisnik"));
        komentar.setIdsmestaj(rs.getLong("idsmestaj"));
        return komentar;
    };

    public KomentarDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Komentar> list() {
        return null;
    }

    @Override
    public void create(Komentar komentar) {

    }

    @Override
    public Optional<Komentar> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Komentar komentar, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
