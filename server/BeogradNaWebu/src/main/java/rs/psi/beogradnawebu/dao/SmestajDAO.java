package rs.psi.beogradnawebu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.List;
import java.util.Optional;

@Component
public class SmestajDAO implements DAO<Smestaj>{

    private static final Logger log = LoggerFactory.getLogger(KomentarDAO.class);
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Smestaj> rowMapper = (rs, rowNum) -> {
        Smestaj smestaj = new Smestaj();
        smestaj.setIdsmestaj(rs.getLong("idsmestaj"));
        smestaj.setOrgPutanja(rs.getString("org_putanja"));
        smestaj.setSlika(rs.getBlob("slika"));
        smestaj.setBrojLajkova(rs.getLong("broj_lajkova"));
        smestaj.setLokacija(rs.getString("lokacija"));
        smestaj.setBrojSoba(rs.getDouble("broj_soba"));
        smestaj.setSpratonost(rs.getLong("spratnost"));
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
        return null;
    }

    @Override
    public void create(Smestaj smestaj) {

    }

    @Override
    public Optional<Smestaj> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Smestaj smestaj, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
