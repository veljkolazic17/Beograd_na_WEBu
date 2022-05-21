package rs.psi.beogradnawebu.dto;

import rs.psi.beogradnawebu.annotations.ValidnoKorime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class LoginDTO {
    @NotNull
    @NotEmpty
    @ValidnoKorime // rucno napravljen validator, korime ne sme postojati
    private String korime;

    @NotNull
    @NotEmpty
    private String sifra;

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
}
