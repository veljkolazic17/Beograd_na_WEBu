package rs.psi.beogradnawebu.misc;

public class Filter {
    private String Lokacija;
    private double BrojSoba;
    private String TipSmestajaIme;
    private int KvadraturaOd;
    private int KvadraturaDo;
    private double CenaOd;
    private double CenaDo;
    private int Spratnost;
    private boolean ImaLift;

    public String getLokacija() {
        return Lokacija;
    }

    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }

    public double getBrojSoba() {
        return BrojSoba;
    }

    public void setBrojSoba(double brojSoba) {
        BrojSoba = brojSoba;
    }

    public String getTipSmestajaIme() {
        return TipSmestajaIme;
    }

    public void setTipSmestajaIme(String tipSmestajaIme) {
        TipSmestajaIme = tipSmestajaIme;
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

    public int getSpratnost() {
        return Spratnost;
    }

    public void setSpratnost(int spratnost) {
        Spratnost = spratnost;
    }

    public boolean isImaLift() {
        return ImaLift;
    }

    public void setImaLift(boolean imaLift) {
        ImaLift = imaLift;
    }
}
