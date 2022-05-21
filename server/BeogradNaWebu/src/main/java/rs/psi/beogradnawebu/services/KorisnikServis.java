package rs.psi.beogradnawebu.services;

import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dto.RegistracijaDTO;
import rs.psi.beogradnawebu.recalg.exceptions.KorisnikVecPostojiException;
import rs.psi.beogradnawebu.model.Korisnik;


@Service
public class KorisnikServis {
    private KorisnikDAO korisnikDAO;

    public KorisnikServis(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    public Korisnik registrujNovogKorisnika(RegistracijaDTO regDTO) throws KorisnikVecPostojiException {
        if (imePostoji(regDTO.getKorime())) {
            throw new KorisnikVecPostojiException("Korisničko ime je već prijavljeno!");
        }

        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setEmail(regDTO.getEmail());
        noviKorisnik.setSifra(regDTO.getSifra());
        noviKorisnik.setKorisnickoime(regDTO.getKorime());
        noviKorisnik.setEpredlog(0);
        noviKorisnik.setUloga(0);
        korisnikDAO.create(noviKorisnik);
        return noviKorisnik;
    }
    private boolean imePostoji(String korime) {
        return !korisnikDAO.getUserByUsername(korime).isEmpty();
    }
}
