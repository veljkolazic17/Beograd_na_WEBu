package rs.psi.beogradnawebu.model;


public class Korisnik {

  private long idkorisnik;
  private String korisnickoime;
  private String email;
  private String sifra;
  private long uloga;
  private long epredlog;


  public long getIdkorisnik() {
    return idkorisnik;
  }

  public void setIdkorisnik(long idkorisnik) {
    this.idkorisnik = idkorisnik;
  }


  public String getKorisnickoime() {
    return korisnickoime;
  }

  public void setKorisnickoime(String korisnickoime) {
    this.korisnickoime = korisnickoime;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getSifra() {
    return sifra;
  }

  public void setSifra(String sifra) {
    this.sifra = sifra;
  }


  public long getUloga() {
    return uloga;
  }

  public void setUloga(long uloga) {
    this.uloga = uloga;
  }


  public long getEpredlog() {
    return epredlog;
  }

  public void setEpredlog(long epredlog) {
    this.epredlog = epredlog;
  }

}
