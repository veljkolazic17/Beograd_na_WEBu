/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.model;


import java.util.List;

/**
 * Kasa koja sluzi za mapiranje reda iz tabele LajkSmestaja
 */
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
