package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.Korisnik;

import java.util.List;
import java.util.Optional;

@Component
public class KorisnikDAO implements DAO<Korisnik> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Korisnik> rowMapper = (rs, rowNum) -> {
        Korisnik korisnik = new Korisnik();
        korisnik.setIdkorisnik(rs.getLong("idkorisnik"));
        korisnik.setKorisnickoime(rs.getString("korisnickoime"));
        korisnik.setEmail(rs.getString("email"));
        korisnik.setSifra(rs.getString("sifra"));
        korisnik.setUloga(rs.getLong("uloga"));
        korisnik.setEpredlog(rs.getLong("epredlog"));
        return korisnik;
    };

    public KorisnikDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Korisnik> list() {
        return null;
    }

    @Override
    public void create(Korisnik korisnik) {

    }

    @Override
    public Optional<Korisnik> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Korisnik korisnik, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
