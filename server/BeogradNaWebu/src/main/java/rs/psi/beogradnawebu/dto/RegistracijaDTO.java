/**
 * Marko Mirkovic 2019/0197
 */

package rs.psi.beogradnawebu.dto;

import rs.psi.beogradnawebu.annotations.JedinstvenEmail;
import rs.psi.beogradnawebu.annotations.ValidanEmail;
import rs.psi.beogradnawebu.annotations.RegistracijaIsteSifre;
import rs.psi.beogradnawebu.annotations.ValidnoKorime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * RegistracijaDTO - klasa za prenosenje informacija tokom registracije
 * @version 1.0
 */
@RegistracijaIsteSifre // rucno napravljen validator, sifre moraju biti jednake
public class RegistracijaDTO {
    @NotNull
    @NotEmpty
    @ValidnoKorime // rucno napravljen validator, korime ne sme postojati
    private String korime;

    @NotNull
    @NotEmpty
    private String sifra;
    private String potvrdaSifre;

    @ValidanEmail // rucno napravljen validator
    @JedinstvenEmail // rucno napravljen validator
    @NotNull
    @NotEmpty
    private String email;

    /**
     * getKorime - dohvatanje korisnickog imena
     * @return
     */
    public String getKorime() {
        return korime;
    }

    /**
     * setKorime - dodeljivanje korisnickog imena
     * @param korime
     */
    public void setKorime(String korime) {
        this.korime = korime;
    }

    /**
     * getSifra - dohvatanje korisnicke sifre
     * @return
     */
    public String getSifra() {
        return sifra;
    }

    /**
     * setSifra - dodeljivanje korisnicke sifre
     * @param sifra
     */
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    /**
     * getPotvrdaSifre - dohvatanje potvrdne sifre
     * @return
     */
    public String getPotvrdaSifre() {
        return potvrdaSifre;
    }

    /**
     * setPotvrdaSifre - dodeljivanje korisnicke sifre
     * @param potvrdaSifre
     */
    public void setPotvrdaSifre(String potvrdaSifre) {
        this.potvrdaSifre = potvrdaSifre;
    }

    /**
     * getEmail - dohvatanje emaila
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail - dodeljivanje emaila
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
