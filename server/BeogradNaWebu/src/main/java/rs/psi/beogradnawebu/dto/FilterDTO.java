package rs.psi.beogradnawebu.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FilterDTO {


    private String Lokacija;

    private String BrojSoba;

    private String TipSmestaja;

    private int KvadraturaOd;

    private int KvadraturaDo;

    private double CenaOd;

    private double CenaDo;

    private boolean NijePrvi;
    private boolean ImaLift;

    public String getLokacija() {
        if(Lokacija == null){
            return "nullLokacija";
        }
        return Lokacija;
    }

    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }

    public String getBrojSoba() {
        if(BrojSoba == null){
            return "nullSoba";
        }
        return BrojSoba;
    }

    public void setBrojSoba(String brojSoba) {
        BrojSoba = brojSoba;
    }

    public String getTipSmestaja() {
        if(TipSmestaja == null){
            return "nullSmestaj";
        }
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

    public boolean isImaLift() {
        return ImaLift;
    }

    public void setImaLift(boolean imaLift) {
        ImaLift = imaLift;
    }

    public boolean isNull(){
        return Lokacija==null;
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
                ", ImaLift=" + ImaLift +
                '}';
    }
}
