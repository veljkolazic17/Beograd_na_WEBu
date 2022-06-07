/**
* Matija Milosevic 2019/0156
* Veljko Lazic 2019/0241
 * Jelena Lucic 2019/0268
* */

package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import rs.psi.beogradnawebu.dto.FilterDTO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.LajkSmestaja;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.model.TipSmestaja;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* Klasa zaduzena za pristup bazi tabele Smestaj
 * @version 1.0
* */
@Component
public class SmestajDAO implements DAO<Smestaj>{

    private static final Logger log = LoggerFactory.getLogger(SmestajDAO.class);
    private final JdbcTemplate jdbcTemplate;
    /**
     * Mapiranje redova tabele u objekat
     * */
    public static RowMapper<Smestaj> rowMapper = (rs, rowNum) -> {
        Smestaj smestaj = new Smestaj();
        smestaj.setIdsmestaj(rs.getLong("idsmestaj"));
        smestaj.setOrgPutanja(rs.getString("org_putanja"));
        smestaj.setBrojLajkova(rs.getLong("broj_lajkova"));
        smestaj.setLokacija(rs.getString("lokacija"));
        smestaj.setBrojSoba(rs.getDouble("broj_soba"));
        smestaj.setSpratonost(rs.getLong("spratonost"));
        smestaj.setImaLift(rs.getLong("ima_lift"));
        smestaj.setIdtipSmestaja(rs.getLong("idtip_smestaja"));
        smestaj.setKvadratura(rs.getLong("kvadratura"));
        smestaj.setCena(rs.getDouble("cena"));
        smestaj.setPostoji(rs.getLong("postoji"));
        smestaj.setBrojSajta(rs.getLong("broj_sajta"));
        smestaj.setSlika(rs.getString("slika"));
        return smestaj;
    };
    /**
     * Kreiranje instance
     * */
    public SmestajDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Vracanje svih smestaja iz baze
     */
    @Override
    public List<Smestaj> list() {
        return jdbcTemplate.query("SELECT * FROM smestaj",rowMapper);
    }


    /**
     * Ubacivanje objekta smestaj u bazu
     * @param smestaj
     */
    @Override
    public void create(Smestaj smestaj) {
        try {
            jdbcTemplate.update("INSERT INTO smestaj (org_putanja,broj_lajkova,lokacija,broj_soba,spratonost,ima_lift,idtip_smestaja,kvadratura,cena,postoji,broj_sajta,slika) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",

                    smestaj.getOrgPutanja(),
                    smestaj.getBrojLajkova(),
                    smestaj.getLokacija(),
                    smestaj.getBrojSoba(),
                    smestaj.getSpratonost(),
                    smestaj.getImaLift(),
                    smestaj.getIdtipSmestaja(),
                    smestaj.getKvadratura(),
                    smestaj.getCena(),
                    smestaj.getPostoji(),
                    smestaj.getBrojSajta(),
                    smestaj.getSlika());

        }
        catch (Exception e){
            log.info("Neispravan smestaj");
        }
    }

    /**
     * Dohvatanje smestaja sa datim ID
     * @param id
     */
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

    /**
     * Update-ovanje smestaja sa datim ID objektom smestaj
     * @param smestaj
     * @param id
     */
    @Override
    public void update(Smestaj smestaj, int id) {
        try{
            jdbcTemplate.update("UPDATE smestaj SET org_putanja = ?,broj_lajkova = ?,lokacija = ?,broj_soba = ?, spratonost = ?, ima_lift = ?, idtip_smestaja = ?, kvadratura = ?, cena = ? , postoji = ? , broj_sajta = ?, slika = ?" +
                            "WHERE idsmestaj = ?"
                    ,smestaj.getOrgPutanja()
                    ,smestaj.getBrojLajkova()
                    ,smestaj.getLokacija()
                    ,smestaj.getBrojSoba()
                    ,smestaj.getSpratonost()
                    ,smestaj.getImaLift()
                    ,smestaj.getIdtipSmestaja()
                    ,smestaj.getKvadratura()
                    ,smestaj.getCena()
                    ,smestaj.getPostoji()
                    ,smestaj.getBrojSajta()
                    ,smestaj.getSlika()
                    ,id);
        }
        catch (Exception e){
            log.info("Nije pronadjen smestaj sa ID: " + id);
        }
    }

    /**
     * Dekrementiranje broja lajkova za smestaje koje je lajkovao korisnik sa datim id-em pri brisanju istorije lajkova korisnika
     * @param idUser
     */
    public void decLikes(int idUser){
        jdbcTemplate.update("update smestaj set broj_lajkova = broj_lajkova - 1 where idsmestaj in " +
                " (select idsmestaj from lajk_smestaja where idkorisnik = ?)",idUser);
    }

    /**
     * Brisanje smestaja sa datim id
     * @param id
     */
    @Override
    public void delete(int id) {
        try {
            jdbcTemplate.update("DELETE FROM smestaj WHERE idsmestaj = ?",id);
        }
        catch (Exception e){
            log.info("Nije pronadjen smestaj sa ID: " + id);
        }
    }

    /**
     * Dohvatanje smestaja iz baze sa zadatim limitom i offsetom
     * @param offset
     * @param limit
     *
     */

    public List<Smestaj> getByOffset(int offset,int limit){
        try {
            return jdbcTemplate.query("SELECT * FROM smestaj LIMIT "+limit+((offset == 0)?"":" offset "+offset*limit),rowMapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Dohvatanje svih smestaja koji ispunjavaju uslove datih filtera
     * @param filters
     * @param offset
     * @return
     */
    public List<Smestaj> searchByFilters(FilterDTO filters,int offset){
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
                            "AND CASE WHEN ? > 0 THEN kvadratura <= ? ELSE TRUE END AND CASE WHEN ? != '%nullLokacija%' THEN lokacija LIKE ? ELSE TRUE"  +
                            " END AND CASE WHEN ? < 3 AND ? > 0 THEN broj_soba = ? WHEN ? >= 3 THEN broj_soba >= ? ELSE TRUE END" +
                            " AND CASE WHEN ? != 0 THEN idtip_smestaja = ?  ELSE TRUE END AND CASE WHEN ? THEN spratonost > 0 ELSE TRUE END " +
                            " AND CASE WHEN ? THEN ima_lift = 1 ELSE TRUE END LIMIT 10 " + ((offset>0)?"offset "+offset*10:""), rowMapper,
                    filters.getCenaOd(), filters.getCenaOd(), filters.getCenaDo(), filters.getCenaDo(),
                    filters.getKvadraturaOd(), filters.getKvadraturaOd(), filters.getKvadraturaDo(), filters.getKvadraturaDo(),
                    "%" + filters.getLokacija() +"%","%" + filters.getLokacija() +"%", mapBrojSoba(filters.getBrojSoba()), mapBrojSoba(filters.getBrojSoba()),
                    mapBrojSoba(filters.getBrojSoba()), mapBrojSoba(filters.getBrojSoba()),
                    mapBrojSoba(filters.getBrojSoba()), id_tip,id_tip, filters.isNijePrvi(), filters.isImaLift());
        }
        catch (Exception e){
            log.info("Neuspesna operacija");
            return null;
        }
    }

    /**
     * Metoda za proveru da li odredjeni smestaj postoji u bazi
     * @param href
     * @return
     */
    public boolean checkIfExist(String href) {
        try {
            Smestaj smestaj = jdbcTemplate.queryForObject("SELECT * FROM smestaj WHERE org_putanja LIKE ?", SmestajDAO.rowMapper, href);
            if(smestaj == null) return false;
            jdbcTemplate.update("UPDATE smestaj SET postoji = ? WHERE idsmestaj = ?", 1, smestaj.getIdsmestaj()); // azuriranje taga na true
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false; // jdbcTemplate.queryForObject ukoliko ne pronadje baca exeption LOL
        }
        catch(Exception e) {
            log.info("Neuspesna operacija exist");
            return false;
        }
    }

    /**
     * Metoda za brisanje smestaja koji ne postoji vise
     * @param brojSajta
     */
    public void deleteWithFalseTag(int brojSajta) {
        try {
            jdbcTemplate.update("DELETE FROM smestaj WHERE postoji = ? AND broj_sajta = ?", 0, brojSajta);
        } catch (Exception e) {
            log.info("Operacija nije uspela");
        }
    }

    /**
     * Metoda koja postavlja tag postoji na 0
     * @param brojSajta
     */
    public void setAllTags(int brojSajta) {
        try {
            jdbcTemplate.update("UPDATE smestaj SET postoji = ? WHERE broj_sajta = ?", 0, brojSajta); // azuriranje tagova na false
        } catch(Exception e) {
            log.info("Neuspesna operacija");
        }
    }

    /**
     * Klasa kojomje predstavljen prosecan lajkovan smestaj korisnika
     */
    public static class AvgData{
        public double cena;
        public double kvadratura;
        public double spratnost;
        public double broj_soba;
    }


    /**
     * Funkcija koja vraca prosecan lajkovan smestaj korisnika
     * @param id
     *
     */
    public AvgData getAvgAcc(long id){

        RowMapper<AvgData> localMapper = (rs, rowNum) -> {
            AvgData avgData = new AvgData();
            avgData.cena = rs.getDouble("avgCena");
            avgData.kvadratura = rs.getDouble("avgKvadratura");
            avgData.spratnost = rs.getDouble("avgSpratnost");
            avgData.broj_soba = rs.getDouble("avgBrojSoba");
            return avgData;
        };
        AvgData avg = null;
        try {
            avg = jdbcTemplate.queryForObject("SELECT AVG(cena) as avgCena,AVG(kvadratura) as avgKvadratura " +
                    ",AVG(spratonost) as avgSpratnost,AVG(broj_soba) as avgBrojSoba " +
                    " FROM smestaj JOIN lajk_smestaja USING(idsmestaj) WHERE idkorisnik = ?",localMapper,id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return avg;
    }

    /**
     * Funkcija koja mapira broj soba smestaja iz stringa u double zbog upita
     * @param str
     * @return
     */
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
