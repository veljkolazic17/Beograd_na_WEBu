package rs.psi.beogradnawebu.model;


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
