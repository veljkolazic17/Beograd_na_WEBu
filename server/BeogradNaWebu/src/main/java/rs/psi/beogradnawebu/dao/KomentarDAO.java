/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 * Jelena Lucic 2019/0268
 */
package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.Komentar;
import rs.psi.beogradnawebu.model.Smestaj;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 * KomentarDAO - Klasa zaduzena za pristup bazi tabele Kometar
 * @version 1.0
 */
@Component
public class KomentarDAO implements DAO<Komentar> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;


    /**
     * Mapiranje redova u objekat
     */
    public static RowMapper<Komentar> rowMapper = (rs, rowNum) -> {
        Komentar komentar = new Komentar();
        komentar.setIdkomentar(rs.getLong("idkomentar"));
        komentar.setTekstKomentara(rs.getString("tekst_komentara"));
        komentar.setIdkorisnik(rs.getInt("idkorisnik"));
        komentar.setIdsmestaj(rs.getLong("idsmestaj"));
        komentar.setBroj_lajkova(rs.getLong("broj_lajkova"));
        return komentar;
    };

    /**
     * Kreiranje instance
     * @param jdbcTemplate
     */
    public KomentarDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Vraca listu svih komentara
     * @return
     */
    @Override
    public List<Komentar> list() {
        return jdbcTemplate.query("SELECT * FROM komentar",rowMapper);

    }

    /**
     * Kreira komentar
     * @param komentar
     */
    @Override
    public void create(Komentar komentar) {
        try {
            jdbcTemplate.update("INSERT INTO komentar (tekst_komentara, idkorisnik, idsmestaj) VALUES (?,?,?)",
                    komentar.getTekstKomentara(),komentar.getIdkorisnik(),komentar.getIdsmestaj());
        }
        catch (Exception e){
            log.info("Neispravan komentar");
        }

    }

    /**
     * Vraca komentar sa zadatim id
     * @param id
     * @return
     */
    @Override
    public Optional<Komentar> get(int id) {
        Optional<Komentar> komentar = null;
        try {
            Komentar komRet = jdbcTemplate.queryForObject("SELECT * FROM komentar WHERE idkomentar = ?",rowMapper,id);
            komentar = Optional.ofNullable(komRet);
        }
        catch (Exception e){
            log.info("Nije pronadjen komenatar sa ID: " + id);
        }
        return komentar;
    }

    /**
     * Updatovanje svih komentara sa zadatim id
     * @param komentar
     * @param id
     */
    @Override
    public void update(Komentar komentar, int id) {
        try {
            jdbcTemplate.update("UPDATE komentar SET tekst_komentara = ?, idkorisnik = ?, idsmestaj = ?, broj_lajkova = ? WHERE idkomentar = ?"
                    , komentar.getTekstKomentara(),komentar.getIdkorisnik(),komentar.getIdsmestaj(), komentar.getBroj_lajkova(), id);
        }
        catch (Exception e){
            log.info("Nije pronadjen komenatar sa ID: " + id);
        }
    }

    /**
     * Brisanje komentara sa id
     * @param id
     */
    @Override
    public void delete(int id) {
        try {
            jdbcTemplate.update("DELETE FROM komentar WHERE idkomentar = ?", id);
        }
        catch (Exception e){
            log.info("Nije pronadjen komenatar sa ID: " + id);
        }
    }

    /**
     * Metoda za dohvatanje maksimalnog ID-a komentara
     * @return
     */
    public Integer maxID() {
        try {
            Integer maxIDKom = jdbcTemplate.queryForObject("SELECT MAX(idkomentar) FROM komentar", Integer.class);
            return maxIDKom;
        } catch (EmptyResultDataAccessException e) {
            return 1; // prazna baza
        } catch (Exception e) {
            log.info("Neuspesna operacija");
            return -1;
        }
    }

    /**
     * Metoda za dohvatanje svih komentara za odredjeni smestaj
     * @param idSmestaj
     * @return
     */
    public List<Komentar> allKomentar(int idSmestaj) {
        List<Komentar> listKomentar = null;
        try {
            listKomentar = jdbcTemplate.query("SELECT * FROM komentar WHERE idsmestaj = ?", rowMapper, idSmestaj);
        }
        catch (Exception e){
            log.info("Nije pronadjen komenatar sa idSmestaj: " + idSmestaj);
        }
        return listKomentar;
    }
}
