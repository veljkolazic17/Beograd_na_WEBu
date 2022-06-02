/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.model;

/**
 * Kasa koja sluzi za mapiranje reda iz tabele Smestaj
 */
public class Smestaj {

  private long idsmestaj;
  private String orgPutanja;
  private long brojLajkova;
  private String lokacija;
  private double brojSoba;
  private long spratonost;
  private long imaLift;
  private long idtipSmestaja;
  private long kvadratura;
  private double cena;
  private long postoji;
  private long brojSajta;
  private String slika;


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


  public long getPostoji() {
    return postoji;
  }

  public void setPostoji(long postoji) {
    this.postoji = postoji;
  }


  public long getBrojSajta() {
    return brojSajta;
  }

  public void setBrojSajta(long brojSajta) {
    this.brojSajta = brojSajta;
  }


  public String getSlika() {
    return slika;
  }

  public void setSlika(String slika) {
    this.slika = slika;
  }

  @Override
  public String toString() {
    return "Smestaj:\n" +
            "\torgPutanja= " + orgPutanja + '\n' +
            "\tlokacija= " + lokacija + '\n' +
            "\tcena= " + cena;
  }
}
