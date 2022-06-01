package rs.psi.beogradnawebu.dto;

import rs.psi.beogradnawebu.annotations.JedinstvenEmail;
import rs.psi.beogradnawebu.annotations.ValidanEmail;
import rs.psi.beogradnawebu.annotations.RegistracijaIsteSifre;
import rs.psi.beogradnawebu.annotations.ValidnoKorime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getPotvrdaSifre() {
        return potvrdaSifre;
    }

    public void setPotvrdaSifre(String potvrdaSifre) {
        this.potvrdaSifre = potvrdaSifre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
