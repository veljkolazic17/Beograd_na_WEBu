package rs.psi.beogradnawebu.dto;

import rs.psi.beogradnawebu.annotations.PripadaSifra;
import rs.psi.beogradnawebu.annotations.PromSifIsteSifre;

/**
 * RegistracijaDTO - klasa za prenosenje informacija tokom promene sifre
 * @version 1.0
 */
@PromSifIsteSifre
public class PromenaSifreDTO {
    @PripadaSifra
    private String staraSifra;
    private String novaSifra;
    private String ponovljenaNovaSifra;

    public String getStaraSifra() {
        return staraSifra;
    }

    public void setStaraSifra(String staraSifra) {
        this.staraSifra = staraSifra;
    }

    public String getNovaSifra() {
        return novaSifra;
    }

    public void setNovaSifra(String novaSifra) {
        this.novaSifra = novaSifra;
    }

    public String getPonovljenaNovaSifra() {
        return ponovljenaNovaSifra;
    }

    public void setPonovljenaNovaSifra(String ponovljenaNovaSifra) {
        this.ponovljenaNovaSifra = ponovljenaNovaSifra;
    }
}
