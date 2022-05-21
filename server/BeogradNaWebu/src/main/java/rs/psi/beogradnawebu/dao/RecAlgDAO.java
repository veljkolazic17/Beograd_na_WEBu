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

@Component
public class RecAlgDAO implements DAO<Recalgdata>{

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    public RecAlgDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public static RowMapper<Recalgdata> rowMapper = (rs, rowNum) -> {
        Recalgdata recalgdata = new Recalgdata();
        recalgdata.setIdkorisnik(rs.getLong("idkorisnik"));
        recalgdata.setRangeMinBrojSoba(rs.getDouble("range_min_broj_soba"));
        recalgdata.setRangeMaxBrojSoba(rs.getDouble("range_max_broj_soba"));
        recalgdata.setRangeMinSpratnost(rs.getInt("range_min_spratnost"));
        recalgdata.setRangeMaxSpratnost(rs.getInt("range_max_spratnost"));
        recalgdata.setRangeMinImaLift(rs.getInt("range_min_ima_lift"));
        recalgdata.setRangeMaxImaLift(rs.getInt("range_max_ima_lift"));
        recalgdata.setRangeMinKvadratura(rs.getInt("range_min_kvadratura"));
        recalgdata.setRangeMaxKvadratura(rs.getInt("range_max_kvadratura"));
        recalgdata.setRangeMinCena(rs.getDouble("range_min_cena"));
        recalgdata.setRangeMaxCena(rs.getDouble("range_max_cena"));
        recalgdata.setWeightBrojSoba(rs.getDouble("weight_broj_soba"));
        recalgdata.setWeigthSpratonst(rs.getDouble("weight_spratnost"));
        recalgdata.setWeightImaLift(rs.getDouble("weight_ima_lift"));
        recalgdata.setWeightKvadratura(rs.getDouble("weight_kvadratura"));
        recalgdata.setWeightCena(rs.getDouble("weight_cena"));
        return recalgdata;
    };

    @Override
    public List<Recalgdata> list() {
        List<Recalgdata> recalgdata = jdbcTemplate.query("select * from recalgdata",rowMapper);
        return recalgdata;
    }

    @Override
    public void create(Recalgdata recalgdata) {
        jdbcTemplate.update(
                "insert into recalgdata values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                recalgdata.getIdkorisnik(),
                recalgdata.getRangeMinBrojSoba(),
                recalgdata.getRangeMaxBrojSoba(),
                recalgdata.getRangeMinSpratnost(),
                recalgdata.getRangeMaxSpratnost(),
                recalgdata.getRangeMinImaLift(),
                recalgdata.getRangeMaxImaLift(),
                recalgdata.getRangeMinKvadratura(),
                recalgdata.getRangeMaxKvadratura(),
                recalgdata.getRangeMinCena(),
                recalgdata.getRangeMaxCena(),
                recalgdata.getWeightBrojSoba(),
                recalgdata.getWeigthSpratonst(),
                recalgdata.getWeightImaLift(),
                recalgdata.getWeightKvadratura(),
                recalgdata.getWeightCena()
                );
    }

    @Override
    public Optional<Recalgdata> get(int id) {
        Recalgdata recalgdata = null;
        try{
            recalgdata = jdbcTemplate.queryForObject("select * from recalgdata where idkorisnik = ?",rowMapper,id);
        }catch(Exception exception){
            log.info("Recalgdata nije pronadjen: " + id);
        }
        return Optional.ofNullable(recalgdata);
    }

    @Override
    public void update(Recalgdata recalgdata, int id) {
        try{
            jdbcTemplate.update("update recalgdata set idkorisnik = ?, range_min_broj_soba = ?, range_max_broj_soba = ?, range_min_spratnost = ?, range_max_spratnost = ?, range_min_ima_lift = ?, range_max_ima_lift = ?, range_min_kvadratura = ?, range_max_kvadratura = ?, range_min_cena = ?, range_max_cena = ?, weight_broj_soba = ?, weight_spratnost = ?, weight_ima_lift = ?, weight_kvadratura = ?, weight_cena = ? where idkorisnik = ?",
                    recalgdata.getIdkorisnik(),
                    recalgdata.getRangeMinBrojSoba(),
                    recalgdata.getRangeMaxBrojSoba(),
                    recalgdata.getRangeMinSpratnost(),
                    recalgdata.getRangeMaxSpratnost(),
                    recalgdata.getRangeMinImaLift(),
                    recalgdata.getRangeMaxImaLift(),
                    recalgdata.getRangeMinKvadratura(),
                    recalgdata.getRangeMaxKvadratura(),
                    recalgdata.getRangeMinCena(),
                    recalgdata.getRangeMaxCena(),
                    recalgdata.getWeightBrojSoba(),
                    recalgdata.getWeigthSpratonst(),
                    recalgdata.getWeightImaLift(),
                    recalgdata.getWeightKvadratura(),
                    recalgdata.getWeightCena(),
                    id
            );
        } catch (Exception exception){
            log.info("Nije moguce promeniti recalgdata");
        }
    }

    @Override
    public void delete(int id) {
        try {
            jdbcTemplate.update("delete from recalgdata where idkorisnik = ?",id);
        } catch (Exception e){
            log.info("Nije moguce izbrisati recalgdata");
        }
    }
}