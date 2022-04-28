package rs.psi.beogradnawebu.model;

public class LajkKomentara {

  private long idkomentar;
  private long idkorisnik;


  public long getIdkomentar() {
    return idkomentar;
  }

  public void setIdkomentar(long idkomentar) {
    this.idkomentar = idkomentar;
  }


  public long getIdkorisnik() {
    return idkorisnik;
  }

  public void setIdkorisnik(long idkorisnik) {
    this.idkorisnik = idkorisnik;
  }

  @Override
  public String toString() {
    return "LajkKomentara{" +
            "idkomentar=" + idkomentar +
            ", idkorisnik=" + idkorisnik +
            '}';
  }
}
