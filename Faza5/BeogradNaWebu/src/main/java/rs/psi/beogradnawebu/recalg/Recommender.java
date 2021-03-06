/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */

package rs.psi.beogradnawebu.recalg;

import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;

/**
 * Interfejs za preporucivanje smestaja
 * @version 1.0
 */
public interface Recommender {
    /**
     Funkcija update se poziva svaki put kad korisnik(user) lajkuje smestaj(liked).
     Ona podesava tezine koje oznacavaju prioritet atributa smestaja koje korisnik preferira.
     @param user
     @param liked
    */
    void update(Korisnik user, Smestaj liked);

    /**
     Funkcija recommend se poziva kada se pojavi novi smestaj(accommodation).
     Ona proverava da li korisniku(user) odgovara dati smestaj i vraca true ukoliko je tako.
     U suprotnom vraca false.
     @param data
     @param avgAcc
     @param accommodation
    */
    boolean recommend(Recalgdata data, SmestajDAO.AvgData avgAcc, Smestaj accommodation);

   /**
    Funkcija koja updatuje u bazi maksimume i minimume lajkova korisnika
    @param user
    @param  accommodation
   */
    int updateRange(Korisnik user, Smestaj accommodation);
}
