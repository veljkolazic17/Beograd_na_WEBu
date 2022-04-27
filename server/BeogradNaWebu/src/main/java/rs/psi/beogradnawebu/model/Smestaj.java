package rs.psi.beogradnawebu.model;


import java.sql.Blob;

public class Smestaj {

  private long idsmestaj;
  private String orgPutanja;
  private Blob slika;
  private long brojLajkova;
  private String lokacija;
  private double brojSoba;
  private long spratonost;
  private long imaLift;
  private long idtipSmestaja;
  private long kvadratura;
  private double cena;


  public long getIdsmestaj() {
    return idsmestaj;
  }

  public void setIdsmestaj(long idsmestaj) {
    this.idsmestaj = idsmestaj;
  }


  public String getOrgPutanja() {
    return orgPutanja;
  }

  public void setOrgPutanja(String orgPutanja) {
    this.orgPutanja = orgPutanja;
  }


  public Blob getSlika() {
    return slika;
  }

  public void setSlika(Blob slika) {
    this.slika = slika;
  }


  public long getBrojLajkova() {
    return brojLajkova;
  }

  public void setBrojLajkova(long brojLajkova) {
    this.brojLajkova = brojLajkova;
  }


  public String getLokacija() {
    return lokacija;
  }

  public void setLokacija(String lokacija) {
    this.lokacija = lokacija;
  }


  public double getBrojSoba() {
    return brojSoba;
  }

  public void setBrojSoba(double brojSoba) {
    this.brojSoba = brojSoba;
  }


  public long getSpratonost() {
    return spratonost;
  }

  public void setSpratonost(long spratonost) {
    this.spratonost = spratonost;
  }


  public long getImaLift() {
    return imaLift;
  }

  public void setImaLift(long imaLift) {
    this.imaLift = imaLift;
  }


  public long getIdtipSmestaja() {
    return idtipSmestaja;
  }

  public void setIdtipSmestaja(long idtipSmestaja) {
    this.idtipSmestaja = idtipSmestaja;
  }


  public long getKvadratura() {
    return kvadratura;
  }

  public void setKvadratura(long kvadratura) {
    this.kvadratura = kvadratura;
  }


  public double getCena() {
    return cena;
  }

  public void setCena(double cena) {
    this.cena = cena;
  }

  @Override
  public String toString() {
    return "Smestaj{" +
            "idsmestaj=" + idsmestaj +
            ", orgPutanja='" + orgPutanja + '\'' +
            ", slika=" + slika +
            ", brojLajkova=" + brojLajkova +
            ", lokacija='" + lokacija + '\'' +
            ", brojSoba=" + brojSoba +
            ", spratonost=" + spratonost +
            ", imaLift=" + imaLift +
            ", idtipSmestaja=" + idtipSmestaja +
            ", kvadratura=" + kvadratura +
            ", cena=" + cena +
            '}';
  }
}
