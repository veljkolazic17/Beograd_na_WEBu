package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import rs.psi.beogradnawebu.dto.FilterDTO;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.model.TipSmestaja;

import java.util.List;
import java.util.Optional;

@Component
public class SmestajDAO implements DAO<Smestaj>{

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private final JdbcTemplate jdbcTemplate;

    public static RowMapper<Smestaj> rowMapper = (rs, rowNum) -> {
        Smestaj smestaj = new Smestaj();
        smestaj.setIdsmestaj(rs.getLong("idsmestaj"));
        smestaj.setOrgPutanja(rs.getString("org_putanja"));
        smestaj.setSlika(rs.getString("slika"));
        smestaj.setBrojLajkova(rs.getLong("broj_lajkova"));
        smestaj.setLokacija(rs.getString("lokacija"));
        smestaj.setBrojSoba(rs.getDouble("broj_soba"));
        smestaj.setSpratonost(rs.getLong("spratonost"));
        smestaj.setImaLift(rs.getLong("ima_lift"));
        smestaj.setIdtipSmestaja(rs.getLong("idtip_smestaja"));
        smestaj.setKvadratura(rs.getLong("kvadratura"));
        smestaj.setCena(rs.getDouble("cena"));
        return smestaj;
    };

    public SmestajDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Smestaj> list() {
        return jdbcTemplate.query("SELECT * FROM smestaj",rowMapper);

    }

    @Override
    public void create(Smestaj smestaj) {
       try {
            jdbcTemplate.update("INSERT INTO smestaj (org_putanja,slika,broj_lajkova,lokacija,broj_soba,spratonost,ima_lift,idtip_smestaja,kvadratura,cena) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?)",

                    smestaj.getOrgPutanja(),
                    smestaj.getSlika(),
                    smestaj.getBrojLajkova(),
                    smestaj.getLokacija(),
                    smestaj.getBrojSoba(),
                    smestaj.getSpratonost(),
                    smestaj.getImaLift(),
                    smestaj.getIdtipSmestaja(),
                    smestaj.getKvadratura(),
                    smestaj.getCena());
        }
        catch (Exception e){
            log.info("Neispravan smestaj");
       }

    }

    @Override
    public Optional<Smestaj> get(int id) {
        Optional<Smestaj> smestaj = Optional.empty();
        try {
            Smestaj smestajRet = jdbcTemplate.query("SELECT * FROM smestaj WHERE idsmestaj = ?", rowMapper, id).get(0);
            smestaj =  Optional.ofNullable(smestajRet);
        }
        catch (Exception e){
            log.info("Nije pronadjen smestaj sa ID: " + id);
        }
        return smestaj;
    }

    @Override
    public void update(Smestaj smestaj, int id) {
        try{
            jdbcTemplate.update("UPDATE smestaj SET org_putanja = ?,slika = ?,broj_lajkova = ?,lokacija = ?,broj_soba = ?, spratonost = ?, ima_lift = ?, idtip_smestaja = ?, kvadratura = ?, cena = ? " +
                    "WHERE idsmestaj = ?",smestaj.getOrgPutanja(),smestaj.getSlika(),smestaj.getBrojLajkova(),smestaj.getLokacija(),smestaj.getBrojSoba(),smestaj.getSpratonost(),smestaj.getImaLift(),smestaj.getIdtipSmestaja(),smestaj.getKvadratura(),smestaj.getCena(),id);
        }
        catch (Exception e){
            log.info("Nije pronadjen smestaj sa ID: " + id);
        }

    }

    @Override
    public void delete(int id) {
        try {
            jdbcTemplate.update("DELETE FROM smestaj WHERE idsmestaj = ?",id);

        }
        catch (Exception e){
            log.info("Nije pronadjen smestaj sa ID: " + id);
        }
    }

    public List<Smestaj> searchByFilters(FilterDTO filters){
        try {
            long id_tip;
            if(filters.getTipSmestaja().equals("nullSmestaj")){
                id_tip = 0;
            }

            else {
                TipSmestaja id_tipS = jdbcTemplate.queryForObject("SELECT * FROM tip_smestaja WHERE ime_tipa = ?", TipSmestajaDAO.rowMapper, filters.getTipSmestaja());

                assert id_tipS != null;
                id_tip = id_tipS.getIdtipSmestaja();
            }
            return jdbcTemplate.query("SELECT * FROM smestaj WHERE CASE WHEN ? > 0 THEN cena >= ? ELSE TRUE END " +
                            "AND CASE WHEN ? > 0 THEN cena <= ? ELSE TRUE  END " +
                            "AND CASE WHEN  ? > 0 THEN kvadratura >= ? ELSE TRUE END " +
                            "AND CASE WHEN ? > 0 THEN kvadratura <= ? ELSE TRUE END AND CASE WHEN ? != 'nullLokacija' THEN lokacija = ? ELSE TRUE"  +
                            " END AND CASE WHEN ? < 3 AND ? > 0 THEN broj_soba = ? WHEN ? >= 3 THEN broj_soba >= ? ELSE TRUE END" +
                            " AND CASE WHEN ? != 0 THEN idtip_smestaja = ?  ELSE TRUE END AND CASE WHEN ? THEN spratonost > 0 ELSE TRUE END " +
                            " AND CASE WHEN ? THEN ima_lift = 1 ELSE TRUE END ", rowMapper,
                    filters.getCenaOd(), filters.getCenaOd(), filters.getCenaDo(), filters.getCenaDo(),
                    filters.getKvadraturaOd(), filters.getKvadraturaOd(), filters.getKvadraturaDo(), filters.getKvadraturaDo(),
                    filters.getLokacija(),filters.getLokacija(), mapBrojSoba(filters.getBrojSoba()), mapBrojSoba(filters.getBrojSoba()),
                    mapBrojSoba(filters.getBrojSoba()), mapBrojSoba(filters.getBrojSoba()),
                    mapBrojSoba(filters.getBrojSoba()), id_tip,id_tip, filters.isNijePrvi(), filters.isImaLift());
        }
        catch (Exception e){
            log.info("Neuspesna operacija");
            return null;
        }
    }


    public Smestaj getAvgAcc(long id){
        List<LajkSmestaja> lajkovi = jdbcTemplate.query("SELECT * FROM lajk_smestaja WHERE idkorisnik = ?",LajkSmestajaCDAO.rowMapper,id);
        int numOfLikes = lajkovi.size();
        Smestaj avgSmestaj = new Smestaj();
        avgSmestaj.setCena(0);
        avgSmestaj.setBrojSoba(0);
        avgSmestaj.setImaLift(0);
        avgSmestaj.setSpratonost(0);
        avgSmestaj.setKvadratura(0);

        for(LajkSmestaja l : lajkovi){
            Smestaj s = jdbcTemplate.query("SELECT * FROM smestaj WHERE idsmestaj = ?",rowMapper,l.getIdsmestaj()).get(0);
            avgSmestaj.setKvadratura(avgSmestaj.getKvadratura()+s.getKvadratura());
            avgSmestaj.setSpratonost(avgSmestaj.getSpratonost() + s.getSpratonost());
            avgSmestaj.setImaLift(avgSmestaj.getImaLift() + s.getImaLift());
            avgSmestaj.setBrojSoba(avgSmestaj.getBrojSoba() + s.getBrojSoba());
            avgSmestaj.setCena(avgSmestaj.getCena() + s.getCena());
        }
        avgSmestaj.setKvadratura(avgSmestaj.getKvadratura()/numOfLikes);
        avgSmestaj.setSpratonost(avgSmestaj.getSpratonost()/numOfLikes);
        avgSmestaj.setImaLift(avgSmestaj.getImaLift()/numOfLikes);
        avgSmestaj.setBrojSoba(avgSmestaj.getBrojSoba()/numOfLikes);
        avgSmestaj.setCena(avgSmestaj.getCena()/numOfLikes);
        return  avgSmestaj;

    }

    public double mapBrojSoba(String str){
        return switch (str) {
            case "nullSoba" -> -1;
            case "Garsonjera" -> 0.5;
            case "Jednosoban" -> 1;
            case "Jednoiposoban" -> 1.5;
            case "Dvosoban" -> 2;
            case "Dvoiposoban" -> 2.5;
            case "TrosobanIPreko" -> 3;
            default -> 0;
        };
    }
}
