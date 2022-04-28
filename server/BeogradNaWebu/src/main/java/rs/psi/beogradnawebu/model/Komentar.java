package rs.psi.beogradnawebu.model;



public class Komentar {

  private long idkomentar;
  private String tekstKomentara;
  private long idkorisnik;
  private long idsmestaj;


  public long getIdkomentar() {
    return idkomentar;
  }

  public void setIdkomentar(long idkomentar) {
    this.idkomentar = idkomentar;
  }


  public String getTekstKomentara() {
    return tekstKomentara;
  }

  public void setTekstKomentara(String tekstKomentara) {
    this.tekstKomentara = tekstKomentara;
  }


  public long getIdkorisnik() {
    return idkorisnik;
  }

  public void setIdkorisnik(long idkorisnik) {
    this.idkorisnik = idkorisnik;
  }


  public long getIdsmestaj() {
    return idsmestaj;
  }

  public void setIdsmestaj(long idsmestaj) {
    this.idsmestaj = idsmestaj;
  }

  @Override
  public String toString() {
    return "Komentar{" +
            "idkomentar=" + idkomentar +
            ", tekstKomentara='" + tekstKomentara + '\'' +
            ", idkorisnik=" + idkorisnik +
            ", idsmestaj=" + idsmestaj +
            '}';
  }
}
