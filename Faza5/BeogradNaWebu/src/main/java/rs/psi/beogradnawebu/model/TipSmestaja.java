/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.model;

/**
 * Kasa koja sluzi za mapiranje reda iz tabele TipSmestaja
 */
public class TipSmestaja {

  private long idtipSmestaja;
  private String imeTipa;


  public long getIdtipSmestaja() {
    return idtipSmestaja;
  }

  public void setIdtipSmestaja(long idtipSmestaja) {
    this.idtipSmestaja = idtipSmestaja;
  }


  public String getImeTipa() {
    return imeTipa;
  }

  public void setImeTipa(String imeTipa) {
    this.imeTipa = imeTipa;
  }

  @Override
  public String toString() {
    return "TipSmestaja{" +
            "idtipSmestaja=" + idtipSmestaja +
            ", imeTipa='" + imeTipa + '\'' +
            '}';
  }
}
