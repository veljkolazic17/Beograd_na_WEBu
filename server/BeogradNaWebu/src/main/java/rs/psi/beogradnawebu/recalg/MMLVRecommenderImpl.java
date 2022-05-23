package rs.psi.beogradnawebu.recalg;

import org.springframework.stereotype.Component;
import rs.psi.beogradnawebu.dao.LajkSmestajaCDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;

import java.util.Arrays;

//Implementacija klase za preporucivanje smestaja
@Component
public class MMLVRecommenderImpl implements Recommender {
        private final SmestajDAO smestajDAO;
        private final RecAlgDAO recAlgDAO;
        private final LajkSmestajaCDAO lajkSmestajaCDAO;
        public MMLVRecommenderImpl(SmestajDAO smestajDAO, RecAlgDAO recAlgDAO,LajkSmestajaCDAO lajkSmestajaCDAO){
                this.smestajDAO = smestajDAO;
                this.recAlgDAO = recAlgDAO;
                this.lajkSmestajaCDAO = lajkSmestajaCDAO;
        }
        @Override
        public void update(Korisnik user, Smestaj liked) {
                updateRange(user, liked);
                Smestaj lastLiked = lajkSmestajaCDAO.getLast((int)user.getIdkorisnik()).orElse(null);
                Recalgdata recalgdata = recAlgDAO.get((int)user.getIdkorisnik()).orElse(null);

                if(lastLiked == null || recalgdata == null) return;
                double delta;

                delta = (5 - 10 * Math.abs(lastLiked.getCena() - liked.getCena()) / (recalgdata.getRangeMaxCena() - recalgdata.getRangeMinCena())) / 45;
                if (recalgdata.getWeightCena() + delta > 1) recalgdata.setWeightCena(1);
                else recalgdata.setWeightCena(Math.max(recalgdata.getWeightCena() + delta, 0.1));

                delta = (5 - 10 * Math.abs(lastLiked.getKvadratura() - liked.getKvadratura()) / (double)(recalgdata.getRangeMaxKvadratura() - recalgdata.getRangeMinKvadratura())) / 45;
                if (recalgdata.getWeightKvadratura() + delta > 1) recalgdata.setWeightKvadratura(1);
                else recalgdata.setWeightKvadratura(Math.max(recalgdata.getWeightKvadratura() + delta, 0.1));

                delta = (5 - 10 * Math.abs(lastLiked.getBrojSoba() - liked.getBrojSoba()) / (recalgdata.getRangeMaxBrojSoba() - recalgdata.getRangeMinBrojSoba())) / 45;
                if (recalgdata.getWeightBrojSoba() + delta > 1) recalgdata.setWeightBrojSoba(1);
                else recalgdata.setWeightBrojSoba(Math.max(recalgdata.getWeightBrojSoba() + delta, 0.1));

                delta = (5 - 10 * Math.abs(lastLiked.getSpratonost() - liked.getSpratonost()) /(double) (recalgdata.getRangeMaxSpratnost() - recalgdata.getRangeMinSpratnost())) / 45;
                if (recalgdata.getWeigthSpratonst() + delta > 1) recalgdata.setWeigthSpratonst(1);
                else recalgdata.setWeigthSpratonst(Math.max(recalgdata.getWeigthSpratonst() + delta, 0.1));
                recAlgDAO.update(recalgdata,(int) recalgdata.getIdkorisnik());


        }
        @Override
        public boolean recommend(Recalgdata recalgdata, SmestajDAO.AvgData avgAcc, Smestaj accommodation) {
                // Skaliranje svakog atributa smestaja na vrednosti [0.0 - 10.0]

                double scaledAttribute;
                double rating = 0;
                if(recalgdata == null) return false;
                scaledAttribute = 10 - 10 * Math.abs(accommodation.getCena() - avgAcc.cena)
                        / ((Math.max(recalgdata.getRangeMaxCena(),accommodation.getCena()) - Math.min(recalgdata.getRangeMinCena(),accommodation.getCena())));
                rating += scaledAttribute * recalgdata.getWeightCena();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getSpratonost() - avgAcc.spratnost)
                        /(double) (Math.max(recalgdata.getRangeMaxSpratnost(),accommodation.getSpratonost()) - Math.min(recalgdata.getRangeMinSpratnost(),accommodation.getSpratonost()));
                rating += scaledAttribute * recalgdata.getWeigthSpratonst();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getKvadratura() - avgAcc.kvadratura)
                        / (double)((Math.max(recalgdata.getRangeMaxKvadratura(),accommodation.getKvadratura()) - Math.min(recalgdata.getRangeMinKvadratura(),accommodation.getKvadratura())));
                rating += scaledAttribute * recalgdata.getWeightKvadratura();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getBrojSoba() - avgAcc.broj_soba)
                        / ((Math.max(recalgdata.getRangeMaxBrojSoba(),accommodation.getBrojSoba()) - Math.min(recalgdata.getRangeMinBrojSoba(),accommodation.getBrojSoba())));
                rating += scaledAttribute * recalgdata.getWeightBrojSoba();






                rating /= (recalgdata.getWeightBrojSoba() + recalgdata.getWeightCena()+
                        recalgdata.getWeigthSpratonst() + recalgdata.getWeightKvadratura());

                System.out.println(rating);
                // Ukoliko je ocena veca ili jednaka od 7.5, smestaj se preporucuje korisniku
                return rating >= 7.5;
        }
        @Override
        public void updateRange(Korisnik user, Smestaj accommodation) {
                Recalgdata recalgdata = recAlgDAO.get((int)user.getIdkorisnik()).orElse(null);
                if (recalgdata == null) {
                        recalgdata = new Recalgdata();
                        recalgdata.setIdkorisnik(user.getIdkorisnik());
                        recalgdata.setRangeMinCena(accommodation.getCena());
                        recalgdata.setRangeMaxCena(accommodation.getCena());
                        recalgdata.setRangeMaxBrojSoba(accommodation.getBrojSoba()+1);
                        recalgdata.setRangeMinBrojSoba(accommodation.getBrojSoba());
                        recalgdata.setRangeMinKvadratura(accommodation.getKvadratura());
                        recalgdata.setRangeMaxKvadratura(accommodation.getKvadratura()+1);
                        recalgdata.setRangeMinSpratnost(accommodation.getSpratonost());
                        recalgdata.setRangeMaxSpratnost(accommodation.getSpratonost()+1);
                        recalgdata.setWeigthSpratonst(0.5);
                        recalgdata.setWeightKvadratura(0.5);
                        recalgdata.setWeightCena(0.5);
                        recalgdata.setWeightBrojSoba(0.5);
                        recAlgDAO.create(recalgdata);
                } else {

                        if (accommodation.getCena() > recalgdata.getRangeMaxCena()) {

                                recalgdata.setRangeMaxCena(accommodation.getCena());


                        } else if (accommodation.getCena() < recalgdata.getRangeMinCena()) {
                                recalgdata.setRangeMinCena(accommodation.getCena());


                        }
                        else{
                                ShiftParam shiftParam = shiftRange(recalgdata.getRangeMinCena(),recalgdata.getRangeMaxCena(),accommodation.getCena());
                                if(shiftParam.ref.equals("MIN")){
                                        recalgdata.setRangeMinCena(recalgdata.getRangeMinCena() + shiftParam.shift);
                                }else {
                                        recalgdata.setRangeMaxCena(recalgdata.getRangeMaxCena() - shiftParam.shift);
                                }

                        }
                        if (accommodation.getSpratonost() > recalgdata.getRangeMaxSpratnost()) {
                                recalgdata.setRangeMaxSpratnost(accommodation.getSpratonost());
                        } else if (accommodation.getSpratonost() < recalgdata.getRangeMinSpratnost()) {
                                recalgdata.setRangeMinSpratnost(accommodation.getSpratonost());


                        }
                        else {
                                ShiftParam sh = shiftRange(recalgdata.getRangeMinSpratnost(),recalgdata.getRangeMaxSpratnost(),accommodation.getSpratonost());
                                if(sh.ref.equals("MIN")){
                                        recalgdata.setRangeMinSpratnost((long)(recalgdata.getRangeMinSpratnost() + sh.shift));
                                }
                                else {
                                        recalgdata.setRangeMaxSpratnost((long)(recalgdata.getRangeMaxSpratnost() - sh.shift));
                                }
                        }
                        if (accommodation.getKvadratura() > recalgdata.getRangeMaxKvadratura()) {
                                recalgdata.setRangeMaxKvadratura(accommodation.getKvadratura());


                        } else if (accommodation.getKvadratura() < recalgdata.getRangeMinKvadratura()) {
                                recalgdata.setRangeMinKvadratura(accommodation.getKvadratura());


                        }
                        else{
                                ShiftParam shiftParam = shiftRange(recalgdata.getRangeMinKvadratura(),recalgdata.getRangeMaxKvadratura(),accommodation.getKvadratura());
                                if(shiftParam.ref.equals("MIN")){
                                        recalgdata.setRangeMinKvadratura((long)(recalgdata.getRangeMinKvadratura() + shiftParam.shift));
                                }else {
                                        recalgdata.setRangeMaxKvadratura((long)(recalgdata.getRangeMaxKvadratura() - shiftParam.shift));
                                }
                        }
                        if (accommodation.getBrojSoba() > recalgdata.getRangeMaxBrojSoba()) {
                                recalgdata.setRangeMaxBrojSoba(accommodation.getBrojSoba());


                        } else if (accommodation.getBrojSoba()< recalgdata.getRangeMinBrojSoba()) {
                                recalgdata.setRangeMinBrojSoba(accommodation.getBrojSoba());


                        }
                        else {
                                ShiftParam shiftParam = shiftRange(recalgdata.getRangeMinBrojSoba(),recalgdata.getRangeMaxBrojSoba(),accommodation.getBrojSoba());
                                if(shiftParam.ref.equals("MIN")){
                                        recalgdata.setRangeMinBrojSoba(recalgdata.getRangeMinBrojSoba() + shiftParam.shift);
                                }
                                else {
                                        recalgdata.setRangeMaxBrojSoba(recalgdata.getRangeMaxBrojSoba() - shiftParam.shift);
                                }
                        }

                        recAlgDAO.update(recalgdata,(int)recalgdata.getIdkorisnik());
                }
        }

        // Klasa koju koristimo da odredimo na osnovu cega i koliko da promenimo granice
        private static class ShiftParam{
                double shift;
                String ref;
        }

        private ShiftParam shiftRange(double min,double max,double val){
                double mid = (max + min)/2;
                ShiftParam shiftParam = new ShiftParam();
                if(val > mid){
                        shiftParam.ref = "MIN";
                        shiftParam.shift = Math.min(max - val, val - mid);
                }
                else if(val < mid){
                        shiftParam.ref = "MAX";
                        shiftParam.shift = Math.min(val - min, mid - val);
                }
                return shiftParam;
        }
}
