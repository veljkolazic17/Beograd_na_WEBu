/**
 * Veljko Lazic 2019/0241
 * Matija Milosevic 2019/0156
 */

package rs.psi.beogradnawebu.recalg;

import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.Arrays;

/**
 * Klasa koja sluzi za preporucivanje smestaja
 */
@Component
public class MMLVRecommenderImpl implements Recommender {
        private final SmestajDAO smestajDAO;
        private final RecAlgDAO recAlgDAO;
        private final LajkSmestajaCDAO lajkSmestajaCDAO;

        /**
         * Kreiranje instance
         * @param smestajDAO
         * @param recAlgDAO
         * @param lajkSmestajaCDAO
         */
        public MMLVRecommenderImpl(SmestajDAO smestajDAO, RecAlgDAO recAlgDAO,LajkSmestajaCDAO lajkSmestajaCDAO){
                this.smestajDAO = smestajDAO;
                this.recAlgDAO = recAlgDAO;
                this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        }

        /**
         * Funckija koja updatuje tezine(odredjuju vaznost odredjenih atributa kao sto su cena, kvadratura itd.) korisnika user u odnosnu na novolajkovani smestaj liked
         * @param user
         * @param liked
         */
        @Override
        public void update(Korisnik user, Smestaj liked) {
                int res = updateRange(user, liked);
                if(res == 0) return;
                Smestaj lastLiked = lajkSmestajaCDAO.getLast((int)user.getIdkorisnik()).orElse(null);
                Recalgdata recalgdata = recAlgDAO.get((int)user.getIdkorisnik()).orElse(null);

                if(lastLiked == null || recalgdata == null) return;
                double delta;
                if(liked.getCena()>0) {
                        delta = (5 - 10 * Math.abs(lastLiked.getCena() - liked.getCena()) / (recalgdata.getRangeMaxCena() - recalgdata.getRangeMinCena())) / 45;
                        if (recalgdata.getWeightCena() + delta > 1) recalgdata.setWeightCena(1);
                        else recalgdata.setWeightCena(Math.max(recalgdata.getWeightCena() + delta, 0.1));
                }
                if(liked.getKvadratura() > 0) {
                        delta = (5 - 10 * Math.abs(lastLiked.getKvadratura() - liked.getKvadratura()) / (double) (recalgdata.getRangeMaxKvadratura() - recalgdata.getRangeMinKvadratura())) / 45;
                        if (recalgdata.getWeightKvadratura() + delta > 1) recalgdata.setWeightKvadratura(1);
                        else recalgdata.setWeightKvadratura(Math.max(recalgdata.getWeightKvadratura() + delta, 0.1));
                }
                if(liked.getBrojSoba()>0) {
                        delta = (5 - 10 * Math.abs(lastLiked.getBrojSoba() - liked.getBrojSoba()) / (recalgdata.getRangeMaxBrojSoba() - recalgdata.getRangeMinBrojSoba())) / 45;
                        if (recalgdata.getWeightBrojSoba() + delta > 1) recalgdata.setWeightBrojSoba(1);
                        else recalgdata.setWeightBrojSoba(Math.max(recalgdata.getWeightBrojSoba() + delta, 0.1));
                }
                if(liked.getSpratonost()>-1) {
                        delta = (5 - 10 * Math.abs(lastLiked.getSpratonost() - liked.getSpratonost()) / (double) (recalgdata.getRangeMaxSpratnost() - recalgdata.getRangeMinSpratnost())) / 45;
                        if (recalgdata.getWeigthSpratonst() + delta > 1) recalgdata.setWeigthSpratonst(1);
                        else recalgdata.setWeigthSpratonst(Math.max(recalgdata.getWeigthSpratonst() + delta, 0.1));
                }
                recAlgDAO.update(recalgdata,(int) recalgdata.getIdkorisnik());


        }

        /**
         * Funkcija koja na osnovu prosecnog lajkovanog smestaja avgAcc i tezina odredjenih atributa
         * smestaja koje se nalaze u recalgdata datog korisnika, odredjuje da li sistem treba ili ne treba
         * da preporuci smestaj accomodation korisniku na osnovu izracunate ocene(ukoliko je veca ili jednaka od 7.5 smestaj se preporucuje).
         * @param recalgdata
         * @param avgAcc
         * @param accommodation
         * @return
         */
        @Override
        public boolean recommend(Recalgdata recalgdata, SmestajDAO.AvgData avgAcc, Smestaj accommodation) {
                // Skaliranje svakog atributa smestaja na vrednosti [0.0 - 10.0]

                double scaledAttribute;
                double rating = 0;
                if(recalgdata == null) return false;
                int cenaNotNull = 0,spratnostNotNull = 0,kvadraturaNotNull = 0, brojSobaNotNull = 0;
                if(accommodation.getCena()>0) {
                        cenaNotNull = 1;
                        scaledAttribute = 10 - 10 * Math.abs(accommodation.getCena() - avgAcc.cena)
                                / ((Math.max(recalgdata.getRangeMaxCena(), accommodation.getCena()) - Math.min(recalgdata.getRangeMinCena(), accommodation.getCena())));
                        rating += scaledAttribute * recalgdata.getWeightCena();
                }
                if(accommodation.getSpratonost() > -1) {
                        spratnostNotNull = 1;
                        scaledAttribute = 10 - 10 * Math.abs(accommodation.getSpratonost() - avgAcc.spratnost)
                                / (double) (Math.max(recalgdata.getRangeMaxSpratnost(), accommodation.getSpratonost()) - Math.min(recalgdata.getRangeMinSpratnost(), accommodation.getSpratonost()));
                        rating += scaledAttribute * recalgdata.getWeigthSpratonst();
                }
                if(accommodation.getKvadratura() > 0) {
                        kvadraturaNotNull = 1;
                        scaledAttribute = 10 - 10 * Math.abs(accommodation.getKvadratura() - avgAcc.kvadratura)
                                / (double) ((Math.max(recalgdata.getRangeMaxKvadratura(), accommodation.getKvadratura()) - Math.min(recalgdata.getRangeMinKvadratura(), accommodation.getKvadratura())));
                        rating += scaledAttribute * recalgdata.getWeightKvadratura();
                }
                if(accommodation.getBrojSoba() > 0) {
                        brojSobaNotNull = 1;
                        scaledAttribute = 10 - 10 * Math.abs(accommodation.getBrojSoba() - avgAcc.broj_soba)
                                / ((Math.max(recalgdata.getRangeMaxBrojSoba(), accommodation.getBrojSoba()) - Math.min(recalgdata.getRangeMinBrojSoba(), accommodation.getBrojSoba())));
                        rating += scaledAttribute * recalgdata.getWeightBrojSoba();
                }

                if(cenaNotNull == 1 || spratnostNotNull == 1 || kvadraturaNotNull == 1 || brojSobaNotNull == 1) {
                        rating /= (recalgdata.getWeightBrojSoba()*brojSobaNotNull + recalgdata.getWeightCena()*cenaNotNull +
                                recalgdata.getWeigthSpratonst()*spratnostNotNull + recalgdata.getWeightKvadratura()*kvadraturaNotNull);

                        // System.out.println(rating);
                        // Ukoliko je ocena veca ili jednaka od 7.5, smestaj se preporucuje korisniku
                        return rating >= 7.5;
                }
                return false;
        }

        /**
         * Funkcija koja se poziva pri update-ovanju recalgdata datog korisnika.
         * Menja opsege odredjenih atributa kao sto su cena,kvadratura itd.
         * Ovi opsezi se koriste prilikom provere da li smestaj treba da se preporuci.
         * @param user
         * @param accommodation
         * @return
         */
        @Override
        public int updateRange(Korisnik user, Smestaj accommodation) {
                Recalgdata recalgdata = recAlgDAO.get((int)user.getIdkorisnik()).orElse(null);
                if (recalgdata == null) {
                        recalgdata = new Recalgdata();
                        recalgdata.setIdkorisnik(user.getIdkorisnik());
                        recalgdata.setRangeMinCena(accommodation.getCena());
                        recalgdata.setRangeMaxCena(accommodation.getCena()*2);
                        recalgdata.setRangeMaxBrojSoba(accommodation.getBrojSoba()*2);
                        recalgdata.setRangeMinBrojSoba(accommodation.getBrojSoba());
                        recalgdata.setRangeMinKvadratura(accommodation.getKvadratura());
                        recalgdata.setRangeMaxKvadratura(accommodation.getKvadratura()*2);
                        if(accommodation.getSpratonost() !=-1) {
                                recalgdata.setRangeMinSpratnost(accommodation.getSpratonost());
                                recalgdata.setRangeMaxSpratnost(accommodation.getSpratonost() * 2);
                        }
                        else {
                                recalgdata.setRangeMinSpratnost(0);
                                recalgdata.setRangeMaxSpratnost(1);
                        }
                        recalgdata.setWeigthSpratonst(0.5);
                        recalgdata.setWeightKvadratura(0.5);
                        recalgdata.setWeightCena(0.5);
                        recalgdata.setWeightBrojSoba(0.5);
                        recAlgDAO.create(recalgdata);
                        return 0;
                } else {
                        if(accommodation.getCena() > 0) {
                                if (accommodation.getCena() > recalgdata.getRangeMaxCena()) {

                                        recalgdata.setRangeMaxCena(accommodation.getCena());


                                } else if (accommodation.getCena() < recalgdata.getRangeMinCena()) {
                                        recalgdata.setRangeMinCena(accommodation.getCena());


                                } else {
                                        ShiftParam shiftParam = shiftRange(recalgdata.getRangeMinCena(), recalgdata.getRangeMaxCena(), accommodation.getCena());
                                        if (shiftParam.ref.equals("MIN")) {
                                                recalgdata.setRangeMinCena(recalgdata.getRangeMinCena() + shiftParam.shift);
                                        } else {
                                                recalgdata.setRangeMaxCena(recalgdata.getRangeMaxCena() - shiftParam.shift);
                                        }

                                }
                        }
                        if(accommodation.getSpratonost() > -1) {
                                if (accommodation.getSpratonost() > recalgdata.getRangeMaxSpratnost()) {
                                        recalgdata.setRangeMaxSpratnost(accommodation.getSpratonost());
                                } else if (accommodation.getSpratonost() < recalgdata.getRangeMinSpratnost()) {
                                        recalgdata.setRangeMinSpratnost(accommodation.getSpratonost());


                                } else {
                                        ShiftParam sh = shiftRange(recalgdata.getRangeMinSpratnost(), recalgdata.getRangeMaxSpratnost(), accommodation.getSpratonost());
                                        if (sh.ref.equals("MIN")) {
                                                recalgdata.setRangeMinSpratnost((long) (recalgdata.getRangeMinSpratnost() + sh.shift));
                                        } else {
                                                recalgdata.setRangeMaxSpratnost((long) (recalgdata.getRangeMaxSpratnost() - sh.shift));
                                        }
                                }
                        }
                        if(accommodation.getKvadratura() > 0) {
                                if (accommodation.getKvadratura() > recalgdata.getRangeMaxKvadratura()) {
                                        recalgdata.setRangeMaxKvadratura(accommodation.getKvadratura());


                                } else if (accommodation.getKvadratura() < recalgdata.getRangeMinKvadratura()) {
                                        recalgdata.setRangeMinKvadratura(accommodation.getKvadratura());


                                } else {
                                        ShiftParam shiftParam = shiftRange(recalgdata.getRangeMinKvadratura(), recalgdata.getRangeMaxKvadratura(), accommodation.getKvadratura());
                                        if (shiftParam.ref.equals("MIN")) {
                                                recalgdata.setRangeMinKvadratura((long) (recalgdata.getRangeMinKvadratura() + shiftParam.shift));
                                        } else {
                                                recalgdata.setRangeMaxKvadratura((long) (recalgdata.getRangeMaxKvadratura() - shiftParam.shift));
                                        }
                                }
                        }
                        if(accommodation.getBrojSoba() > 0) {
                                if (accommodation.getBrojSoba() > recalgdata.getRangeMaxBrojSoba()) {
                                        recalgdata.setRangeMaxBrojSoba(accommodation.getBrojSoba());


                                } else if (accommodation.getBrojSoba() < recalgdata.getRangeMinBrojSoba()) {
                                        recalgdata.setRangeMinBrojSoba(accommodation.getBrojSoba());


                                } else {
                                        ShiftParam shiftParam = shiftRange(recalgdata.getRangeMinBrojSoba(), recalgdata.getRangeMaxBrojSoba(), accommodation.getBrojSoba());
                                        if (shiftParam.ref.equals("MIN")) {
                                                recalgdata.setRangeMinBrojSoba(recalgdata.getRangeMinBrojSoba() + shiftParam.shift);
                                        } else {
                                                recalgdata.setRangeMaxBrojSoba(recalgdata.getRangeMaxBrojSoba() - shiftParam.shift);
                                        }
                                }
                        }

                        recAlgDAO.update(recalgdata,(int)recalgdata.getIdkorisnik());
                        return 1;
                }
        }



        /**
         * Klasa koja predstavlja parametar shiftovanja koji se koristi prilikom promene opsega atributa.
         */
        private static class ShiftParam{
                double shift;
                String ref;
        }

        /**
         * Funkcija koja racuna koliko i u kom smeru treba shiftovati opsege atributa.
         * @param min
         * @param max
         * @param val
         * @return
         */
        private ShiftParam shiftRange(double min,double max,double val){
                double mid = (max + min)/2;
                ShiftParam shiftParam = new ShiftParam();
                if(val > mid){
                        shiftParam.ref = "MIN";
                        shiftParam.shift = Math.min(max - val, val - mid);
                }
                else if(val <= mid){
                        shiftParam.ref = "MAX";
                        shiftParam.shift = Math.min(val - min, mid - val);
                }
                return shiftParam;
        }
}
