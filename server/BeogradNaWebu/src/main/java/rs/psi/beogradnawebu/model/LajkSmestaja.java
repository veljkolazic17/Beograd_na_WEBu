package rs.psi.beogradnawebu.model;


import java.util.List;

public class LajkSmestaja {

  private long idkorisnik;
  private long idsmestaj;


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
    return "LajkSmestaja{" +
            "idkorisnik=" + idkorisnik +
            ", idsmestaj=" + idsmestaj +
            '}';
  }
}
