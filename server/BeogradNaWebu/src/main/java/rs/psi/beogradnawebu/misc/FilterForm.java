package rs.psi.beogradnawebu.misc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FilterForm {

    @NotNull
    private String Lokacija;
    @NotNull
    private String BrojSoba;
    @NotNull
    private String TipSmestaja;
    @NotNull
    @Size(min = 1, max = 1000)
    private int KvadraturaOd;
    @NotNull
    @Size(min = 1, max = 1000)
    private int KvadraturaDo;
    @NotNull
    @Size(min = 1, max = 10000)
    private double CenaOd;
    @NotNull
    @Size(min = 1, max = 10000)
    private double CenaDo;

    private boolean NijePrvi;
    private boolean NijePoslednji;
    private boolean ImaLift;

    public String getLokacija() {
        return Lokacija;
    }

    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }

    public String getBrojSoba() {
        return BrojSoba;
    }

    public void setBrojSoba(String brojSoba) {
        BrojSoba = brojSoba;
    }

    public String getTipSmestaja() {
        return TipSmestaja;
    }

    public void setTipSmestaja(String tipSmestaja) {
        TipSmestaja = tipSmestaja;
    }

    public int getKvadraturaOd() {
        return KvadraturaOd;
    }

    public void setKvadraturaOd(int kvadraturaOd) {
        KvadraturaOd = kvadraturaOd;
    }

    public int getKvadraturaDo() {
        return KvadraturaDo;
    }

    public void setKvadraturaDo(int kvadraturaDo) {
        KvadraturaDo = kvadraturaDo;
    }

    public double getCenaOd() {
        return CenaOd;
    }

    public void setCenaOd(double cenaOd) {
        CenaOd = cenaOd;
    }

    public double getCenaDo() {
        return CenaDo;
    }

    public void setCenaDo(double cenaDo) {
        CenaDo = cenaDo;
    }

    public boolean isNijePrvi() {
        return NijePrvi;
    }

    public void setNijePrvi(boolean nijePrvi) {
        NijePrvi = nijePrvi;
    }

    public boolean isNijePoslednji() {
        return NijePoslednji;
    }

    public void setNijePoslednji(boolean nijePoslednji) {
        NijePoslednji = nijePoslednji;
    }

    public boolean isImaLift() {
        return ImaLift;
    }

    public void setImaLift(boolean imaLift) {
        ImaLift = imaLift;
    }

    @Override
    public String toString() {
        return "FilterForm{" +
                "Lokacija='" + Lokacija + '\'' +
                ", BrojSoba='" + BrojSoba + '\'' +
                ", TipSmestaja='" + TipSmestaja + '\'' +
                ", KvadraturaOd=" + KvadraturaOd +
                ", KvadraturaDo=" + KvadraturaDo +
                ", CenaOd=" + CenaOd +
                ", CenaDo=" + CenaDo +
                ", NijePrvi=" + NijePrvi +
                ", NijePoslednji=" + NijePoslednji +
                ", ImaLift=" + ImaLift +
                '}';
    }
}
