/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */

package rs.psi.beogradnawebu.model;

/**
 * Kasa koja sluzi za mapiranje reda iz tabele Komentar
 */
public class Komentar {

  private long idkomentar;
  private String tekstKomentara;
  private long idkorisnik;
  private long idsmestaj;

  private long broj_lajkova;

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

  public long getBroj_lajkova() {
    return broj_lajkova;
  }

  public void setBroj_lajkova(long broj_lajkova) {
    this.broj_lajkova = broj_lajkova;
  }

  @Override
  public String toString() {
    return "Komentar{" +
            "idkomentar=" + idkomentar +
            ", tekstKomentara='" + tekstKomentara + '\'' +
            ", idkorisnik=" + idkorisnik +
            ", idsmestaj=" + idsmestaj +
            ", broj_lajkova=" + broj_lajkova +
            '}';
  }
}
