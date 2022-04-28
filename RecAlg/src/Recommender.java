
//Interfejs za preporucivanje smestaja
public interface Recommender {
    /* 
       Funkcija update se poziva svaki put kad korisnik(user) lajkuje smestaj(liked).
       Ona podesava tezine koje oznacavaju prioritet atributa smestaja koje korisnik preferira.
    */
    void update(User user,Accommodation liked);

    /* 
       Funkcija recommend se poziva kada se pojavi novi smestaj(accommodation).
       Ona proverava da li korisniku(user) odgovara dati smestaj i vraca true ukoliko je tako.
       U suprotnom vraca false.
    */
    boolean recommend(User user,Accommodation accommodation);

   /* 
      Funkcija koja updatuje u bazi maksimume i minimume lajkova korisnika    
   */
    void updateRange(User user, Accommodation accommodation);
}
