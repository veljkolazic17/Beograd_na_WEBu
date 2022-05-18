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

                delta = (5 - 10 * Math.abs(lastLiked.getImaLift() - liked.getImaLift()) / (double)(recalgdata.getRangeMaxImaLift() - recalgdata.getRangeMinImaLift())) / 45;
                if (recalgdata.getWeightImaLift() + delta > 1) recalgdata.setWeightImaLift(1);
                else recalgdata.setWeightImaLift(Math.max(recalgdata.getWeightImaLift() + delta, 0.1));

                delta = (5 - 10 * Math.abs(lastLiked.getSpratonost() - liked.getSpratonost()) /(double) (recalgdata.getRangeMaxSpratnost() - recalgdata.getRangeMinSpratnost())) / 45;
                if (recalgdata.getWeigthSpratonst() + delta > 1) recalgdata.setWeigthSpratonst(1);
                else recalgdata.setWeigthSpratonst(Math.max(recalgdata.getWeigthSpratonst() + delta, 0.1));


        }
        @Override
        public boolean recommend(Korisnik user, Smestaj accommodation) {
                // Skaliranje svakog atributa smestaja na vrednosti [0.0 - 10.0]

                double scaledAttribute;
                double rating = 0;
                Smestaj avgAcc = smestajDAO.getAvgAcc(user.getIdkorisnik());
                Recalgdata recalgdata = recAlgDAO.get((int)user.getIdkorisnik()).orElse(null);
                if(recalgdata == null) return false;
                scaledAttribute = 10 - 10 * Math.abs(accommodation.getCena() - avgAcc.getCena())
                        / ((recalgdata.getRangeMaxCena() - recalgdata.getRangeMinCena()));
                rating += scaledAttribute * recalgdata.getWeightCena();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getSpratonost() - avgAcc.getSpratonost())
                        /(double) ((recalgdata.getRangeMaxSpratnost() - recalgdata.getRangeMinSpratnost()));
                rating += scaledAttribute * recalgdata.getWeigthSpratonst();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getKvadratura() - avgAcc.getKvadratura())
                        / (double)((recalgdata.getRangeMaxKvadratura() - recalgdata.getRangeMinKvadratura()));
                rating += scaledAttribute * recalgdata.getWeightKvadratura();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getBrojSoba() - avgAcc.getBrojSoba())
                        / ((recalgdata.getRangeMaxBrojSoba() - recalgdata.getRangeMinBrojSoba()));
                rating += scaledAttribute * recalgdata.getWeightBrojSoba();

                scaledAttribute = 10 - 10 * Math.abs(accommodation.getImaLift() - avgAcc.getImaLift())
                        /(double) ((recalgdata.getRangeMaxImaLift() - recalgdata.getRangeMinImaLift()));
                rating += scaledAttribute * recalgdata.getWeightImaLift();



                rating /= (recalgdata.getWeightBrojSoba() + recalgdata.getWeightCena() + recalgdata.getWeightImaLift()+
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
                        recalgdata.setRangeMinCena(accommodation.getCena());
                        recalgdata.setRangeMaxCena(accommodation.getCena());
                        recalgdata.setRangeMaxBrojSoba(accommodation.getBrojSoba());
                        recalgdata.setRangeMinBrojSoba(accommodation.getBrojSoba());
                        recalgdata.setRangeMinImaLift(accommodation.getImaLift());
                        recalgdata.setRangeMaxImaLift(accommodation.getImaLift());
                        recalgdata.setRangeMinKvadratura(accommodation.getKvadratura());
                        recalgdata.setRangeMaxKvadratura(accommodation.getKvadratura());
                        recalgdata.setRangeMinSpratnost(accommodation.getSpratonost());
                        recalgdata.setRangeMaxSpratnost(accommodation.getSpratonost());
                        recAlgDAO.create(recalgdata);
                } else {
                        int change = 0;
                        if (accommodation.getCena() > recalgdata.getRangeMaxCena()) {

                                recalgdata.setRangeMaxCena(accommodation.getCena());
                                change = 1;

                        } else if (accommodation.getCena() < recalgdata.getRangeMinCena()) {
                                recalgdata.setRangeMinCena(accommodation.getCena());
                                change = 1;

                        }
                        if (accommodation.getSpratonost() > recalgdata.getRangeMaxSpratnost()) {
                                recalgdata.setRangeMaxSpratnost(accommodation.getSpratonost());
                                change = 1;

                        } else if (accommodation.getSpratonost() < recalgdata.getRangeMinSpratnost()) {
                                recalgdata.setRangeMinSpratnost(accommodation.getSpratonost());
                                change = 1;

                        }
                        if (accommodation.getKvadratura() > recalgdata.getRangeMaxKvadratura()) {
                                recalgdata.setRangeMaxKvadratura(accommodation.getKvadratura());
                                change = 1;

                        } else if (accommodation.getKvadratura() < recalgdata.getRangeMinKvadratura()) {
                                recalgdata.setRangeMinKvadratura(accommodation.getKvadratura());
                                change = 1;

                        }
                        if (accommodation.getBrojSoba() > recalgdata.getRangeMaxBrojSoba()) {
                                recalgdata.setRangeMaxBrojSoba(accommodation.getBrojSoba());
                                change = 1;

                        } else if (accommodation.getBrojSoba()< recalgdata.getRangeMinBrojSoba()) {
                                recalgdata.setRangeMinBrojSoba(accommodation.getBrojSoba());
                                change = 1;

                        }
                        if (accommodation.getImaLift() > recalgdata.getRangeMaxImaLift()) {
                                recalgdata.setRangeMaxImaLift(accommodation.getImaLift());
                                change = 1;

                        } else if (accommodation.getImaLift() < recalgdata.getRangeMinImaLift()) {
                                recalgdata.setRangeMinImaLift(accommodation.getImaLift());
                                change = 1;

                        }
                        if(change == 1)
                                recAlgDAO.update(recalgdata,(int)recalgdata.getIdkorisnik());

                }
        }
}
