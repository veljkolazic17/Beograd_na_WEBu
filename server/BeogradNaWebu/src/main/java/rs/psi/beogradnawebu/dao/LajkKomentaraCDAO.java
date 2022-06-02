/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.LajkKomentara;

import java.util.List;
import java.util.Optional;

/**
 * LajkKomentaraCDAO - Klasa zaduzena za pristup bazi tabele LajkKomentara
 * @version 1.0
 */
@Component
public class LajkKomentaraCDAO implements CDAO<LajkKomentara> {

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    /**
     * Mapiranje redova u objekat
     */
    public static RowMapper<LajkKomentara> rowMapper = (rs, rowNum) -> {
        LajkKomentara lajkKomentara = new LajkKomentara();
        lajkKomentara.setIdkomentar(rs.getLong("idkomentar"));
        lajkKomentara.setIdkorisnik(rs.getLong("idkorisnik"));
        return lajkKomentara;
    };

    /**
     * Kreiranje instance
     * @param jdbcTemplate
     */
    public LajkKomentaraCDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Dohvatanje svih redova iz tabele LajkKomentara
     * @return
     */
    @Override
    public List<LajkKomentara> list() {
        List<LajkKomentara> result = jdbcTemplate.query("select * from lajk_komentara",rowMapper);
        return result;
    }

    /**
     * Kreiranje reda u tabeli LjakKomentar
     * @param lajkKomentara
     */
    @Override
    public void create(LajkKomentara lajkKomentara) {
        jdbcTemplate.update("insert into lajk_komentara (idkomentar,idkorisnik) values (?,?)",lajkKomentara.getIdkomentar(),lajkKomentara.getIdkorisnik());
    }

    /**
     * Dohvatanje reda iz tabele LajkKomentar sa kompozitnim kljucem id[]
     * @param id
     * @return
     */
    @Override
    public Optional<LajkKomentara> get(int[] id) {
        LajkKomentara lajkKomentara = null;
        try{
            lajkKomentara = jdbcTemplate.queryForObject("select * from lajk_komentara where idkomentar = ? and idkorisnik = ?",rowMapper,id[0],id[1]);
        }catch(Exception exception){
            log.info("LajkKomentara nije pronadjen: " + id[0] + " " + id[1]);
        }
        return Optional.ofNullable(lajkKomentara);
    }

    /**
     * Apdejovanje reda u tabeli LajkKomentar sa kompozitnim kljucem id[]
     * @param lajkKomentara
     * @param id
     */
    @Override
    public void update(LajkKomentara lajkKomentara, int[] id){
        try {
            jdbcTemplate.update("update lajk_komentara set idkomentar = ?, idkorisnik = ? where idkomentar = ? and idkorisnik = ?",
                    id[0],id[1],lajkKomentara.getIdkomentar(),lajkKomentara.getIdkorisnik());
        } catch (Exception exception){
            log.info("Nije moguce promeniti lajkkomentara");
        }
    }

    /**
     * Brisanje reda iz tabele LajkKomentar sa kompozitnim kljucem id[]
     * @param id
     */
    @Override
    public void delete(int []id) {
        try {
            jdbcTemplate.update("delete from lajk_komentara where idkomentar = ? and idkorisnik = ?",id[0],id[1]);
        } catch (Exception e){
            log.info("Nije moguce izbrisati lajkkomentara");
        }
    }
}
