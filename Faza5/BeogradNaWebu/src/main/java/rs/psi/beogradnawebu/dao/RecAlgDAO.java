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
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Recalgdata;

import java.util.List;
import java.util.Optional;
/**
 * Klasa zaduzena za pristup bazi tabele recalgdata
 * @version 1.0
 * */
@Component
public class RecAlgDAO implements DAO<Recalgdata>{

    private static final Logger log = LoggerFactory.getLogger(RecAlgDAO.class);
    private JdbcTemplate jdbcTemplate;
    /**
     * Kreiranje instance
     * */
    public RecAlgDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * Mapiranje redova tabele u objekat
     * */
    public static RowMapper<Recalgdata> rowMapper = (rs, rowNum) -> {
        Recalgdata recalgdata = new Recalgdata();
        recalgdata.setIdkorisnik(rs.getInt("idkorisnik"));
        recalgdata.setRangeMinBrojSoba(rs.getDouble("range_min_broj_soba"));
        recalgdata.setRangeMaxBrojSoba(rs.getDouble("range_max_broj_soba"));
        recalgdata.setRangeMinSpratnost(rs.getInt("range_min_spratnost"));
        recalgdata.setRangeMaxSpratnost(rs.getInt("range_max_spratnost"));
        recalgdata.setRangeMinKvadratura(rs.getInt("range_min_kvadratura"));
        recalgdata.setRangeMaxKvadratura(rs.getInt("range_max_kvadratura"));
        recalgdata.setRangeMinCena(rs.getDouble("range_min_cena"));
        recalgdata.setRangeMaxCena(rs.getDouble("range_max_cena"));
        recalgdata.setWeightBrojSoba(rs.getDouble("weight_broj_soba"));
        recalgdata.setWeigthSpratonst(rs.getDouble("weight_spratnost"));
        recalgdata.setWeightKvadratura(rs.getDouble("weight_kvadratura"));
        recalgdata.setWeightCena(rs.getDouble("weight_cena"));
        return recalgdata;
    };
    /**
     * Vracanje svih recalgdata iz baze
     */
    @Override
    public List<Recalgdata> list() {
        List<Recalgdata> recalgdata = jdbcTemplate.query("select * from recalgdata",rowMapper);
        return recalgdata;
    }

    /**
     * Ubacivanje datog objekta Recalgdata u bazu
     * @param recalgdata
     */
    @Override
    public void create(Recalgdata recalgdata) {
        jdbcTemplate.update(
                "insert into recalgdata values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                recalgdata.getIdkorisnik(),
                recalgdata.getRangeMinBrojSoba(),
                recalgdata.getRangeMaxBrojSoba(),
                recalgdata.getRangeMinSpratnost(),
                recalgdata.getRangeMaxSpratnost(),
                recalgdata.getRangeMinKvadratura(),
                recalgdata.getRangeMaxKvadratura(),
                recalgdata.getRangeMinCena(),
                recalgdata.getRangeMaxCena(),
                recalgdata.getWeightBrojSoba(),
                recalgdata.getWeigthSpratonst(),
                recalgdata.getWeightKvadratura(),
                recalgdata.getWeightCena()
                );
    }

    /**
     * Dohvatanje recalgdata sa datim id
     * @param id
     * @return
     */
    @Override
    public Optional<Recalgdata> get(int id) {
        Recalgdata recalgdata = null;
        try{
            recalgdata = jdbcTemplate.queryForObject("select * from recalgdata where idkorisnik = ?;",rowMapper,  id);
        }catch(Exception exception){
            log.info("Recalgdata nije pronadjen: " + id);
        }
        return Optional.ofNullable(recalgdata);
    }

    /**
     * Updatovanje recalgdata sa datim id
     * @param recalgdata
     * @param id
     */
    @Override
    public void update(Recalgdata recalgdata, int id) {
        try{
            jdbcTemplate.update("update recalgdata set idkorisnik = ?, range_min_broj_soba = ?, range_max_broj_soba = ?, range_min_spratnost = ?, range_max_spratnost = ?,  range_min_kvadratura = ?, range_max_kvadratura = ?, range_min_cena = ?, range_max_cena = ?, weight_broj_soba = ?, weight_spratnost = ?,  weight_kvadratura = ?, weight_cena = ? where idkorisnik = ?",
                    recalgdata.getIdkorisnik(),
                    recalgdata.getRangeMinBrojSoba(),
                    recalgdata.getRangeMaxBrojSoba(),
                    recalgdata.getRangeMinSpratnost(),
                    recalgdata.getRangeMaxSpratnost(),
                    recalgdata.getRangeMinKvadratura(),
                    recalgdata.getRangeMaxKvadratura(),
                    recalgdata.getRangeMinCena(),
                    recalgdata.getRangeMaxCena(),
                    recalgdata.getWeightBrojSoba(),
                    recalgdata.getWeigthSpratonst(),
                    recalgdata.getWeightKvadratura(),
                    recalgdata.getWeightCena(),
                    id
            );
        } catch (Exception exception){
            log.info("Nije moguce promeniti recalgdata");
        }
    }

    /**
     * Brisanje recalgdata sa datim id
     * @param id
     */
    @Override
    public void delete(int id) {
        try {
            jdbcTemplate.update("delete from recalgdata where idkorisnik = ?",id);
        } catch (Exception e){
            log.info("Nije moguce izbrisati recalgdata");
        }
    }
}
