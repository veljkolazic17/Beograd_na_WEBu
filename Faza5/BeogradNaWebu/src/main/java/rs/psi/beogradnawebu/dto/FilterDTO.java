/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */

package rs.psi.beogradnawebu.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa koja sluzi za popunjavanje forme i kreiranja objekta filtera koji se kasnije koristi za pretragu smestaja
 * @version 1.0
 */
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

    /**
     * getter za Lokaciju
     * @return
     */
    public String getLokacija() {
        if(Lokacija == null){
            return "nullLokacija";
        }
        return Lokacija;
    }
    /**
     * setter za Lokaciju
     * @return
     */
    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }
    /**
     * getter za BrojSoba
     * @return
     */
    public String getBrojSoba() {
        if(BrojSoba == null){
            return "nullSoba";
        }
        return BrojSoba;
    }
    /**
     * setter za BrojSoba
     * @return
     */
    public void setBrojSoba(String brojSoba) {
        BrojSoba = brojSoba;
    }
    /**
     * getter za TipSmestaja
     * @return
     */
    public String getTipSmestaja() {
        if(TipSmestaja == null){
            return "nullSmestaj";
        }
        return TipSmestaja;
    }
    /**
     * setter za TipSmestaja
     * @return
     */
    public void setTipSmestaja(String tipSmestaja) {
        TipSmestaja = tipSmestaja;
    }
    /**
     * getter za KvadraturaOd
     * @return
     */
    public int getKvadraturaOd() {
        return KvadraturaOd;
    }
    /**
     * setter za KvadraturaOd
     * @return
     */
    public void setKvadraturaOd(int kvadraturaOd) {
        KvadraturaOd = kvadraturaOd;
    }
    /**
     * getter za KvadraturaDo
     * @return
     */
    public int getKvadraturaDo() {
        return KvadraturaDo;
    }
    /**
     * setter za KvadraturaDo
     * @return
     */
    public void setKvadraturaDo(int kvadraturaDo) {
        KvadraturaDo = kvadraturaDo;
    }
    /**
     * getter za CenaOd
     * @return
     */
    public double getCenaOd() {
        return CenaOd;
    }
    /**
     * setter za CenaOd
     * @return
     */
    public void setCenaOd(double cenaOd) {
        CenaOd = cenaOd;
    }
    /**
     * getter za CenaDo
     * @return
     */
    public double getCenaDo() {
        return CenaDo;
    }
    /**
     * setter za CenaDo
     * @return
     */
    public void setCenaDo(double cenaDo) {
        CenaDo = cenaDo;
    }
    /**
     * getter za NijePrvi
     * @return
     */
    public boolean isNijePrvi() {
        return NijePrvi;
    }
    /**
     * setter za NijePrvi
     * @return
     */
    public void setNijePrvi(boolean nijePrvi) {
        NijePrvi = nijePrvi;
    }
    /**
     * getter za Imalift
     * @return
     */
    public boolean isImaLift() {
        return ImaLift;
    }
    /**
     * setter za Imalift
     * @return
     */
    public void setImaLift(boolean imaLift) {
        ImaLift = imaLift;
    }
    /**
     * getter za isNull
     * @return
     */
    public boolean isNull(){
        return Lokacija==null;
    }

    /**
     * toString metoda
     * @return
     */
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
