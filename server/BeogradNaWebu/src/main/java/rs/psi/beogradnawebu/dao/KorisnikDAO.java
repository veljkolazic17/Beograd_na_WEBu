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

    private static final Logger log = LoggerFactory.getLogger(KorisnikDAO.class);
    private JdbcTemplate jdbcTemplate;

    public static RowMapper<Korisnik> rowMapper = (rs, rowNum) -> {
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
        List<Korisnik> result = jdbcTemplate.query("select * from korisnik",rowMapper);
        return result;
    }

    @Override
    public void create(Korisnik korisnik) {
            jdbcTemplate.update("insert into korisnik (korisnickoime,email,sifra,uloga,epredlog) values (?,?,?,?,?)", korisnik.getKorisnickoime(), korisnik.getEmail(), korisnik.getSifra(), korisnik.getUloga(), korisnik.getEpredlog());
    }

    @Override
    public Optional<Korisnik> get(int id) {
        Korisnik korisnik = null;
        try{
            korisnik = jdbcTemplate.queryForObject("select * from korisnik where idkorisnik = ?",rowMapper,id);
        }catch(Exception exception){
           log.info("Korisnik nije pronadjen: " + id);
        }
        return Optional.ofNullable(korisnik);
    }

    @Override
    public void update(Korisnik korisnik, int id) {
        try{
            jdbcTemplate.update("update korisnik set korisnickoime = ?,email = ?, sifra = ?,uloga = ?, epredlog = ? where idkorisnik = ?",
                    korisnik.getKorisnickoime(),korisnik.getEmail(),korisnik.getSifra(),korisnik.getUloga(),korisnik.getEpredlog(),id);
        } catch (Exception exception){
            log.info("Nije moguce promeniti korisnika");
        }
    }

    @Override
    public void delete(int id) {
        try{
            jdbcTemplate.update("delete from korisnik where idkorisnik = ?",id);
        } catch (Exception exception){
            log.info("Nije moguce obrisati korisnika: " + id);
        }
    }

    public Optional<Korisnik> getUserByUsername(String username){
        Korisnik korisnik = null;
        try{
            korisnik = jdbcTemplate.queryForObject("select * from korisnik where korisnickoIme = ?", rowMapper, username);
        }catch(Exception exception){
            log.info("Korisnik nije pronađen: " + username);
        }
        return Optional.ofNullable(korisnik);
    }

    public Optional<Korisnik> getUserByEmail(String email){
        Korisnik korisnik = null;
        try{
            korisnik = jdbcTemplate.queryForObject("select * from korisnik where email = ?", rowMapper, email);
        }catch(Exception exception){
            log.info("Email nije pronađen: " + email);
        }
        return Optional.ofNullable(korisnik);
    }
}
