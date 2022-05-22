package rs.psi.beogradnawebu.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.model.Korisnik;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PodaciKorisnikaServis implements UserDetailsService {

    private KorisnikDAO korisnikDAO;

    public PodaciKorisnikaServis(KorisnikDAO korisnikDAO) {
        this.korisnikDAO = korisnikDAO;
    }

    public UserDetails loadUserByUsername(String korime) throws UsernameNotFoundException {
        Optional<Korisnik> opt = korisnikDAO.getUserByUsername(korime);
        Korisnik korisnik = null;
        if(!opt.isPresent()) {
            throw new UsernameNotFoundException("No user found with username: " + korime);
        } else {
            korisnik = opt.get();
        }

        //standardna poddesavanja za UserDetails
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(1);
        String dozvola = "NONE";
        switch((int)korisnik.getUloga()) {
            case 0: dozvola = "KORISNIK"; break;
            case 1: dozvola = "ADMIN"; break;
        }
        authorities.add(new SimpleGrantedAuthority(dozvola));
        // OBAVEZNO PROMENITI!!!

        return new org.springframework.security.core.userdetails.User(
                korisnik.getKorisnickoime(), korisnik.getSifra(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
    }
}