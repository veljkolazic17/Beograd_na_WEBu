/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.controller.FilterController;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;
import rs.psi.beogradnawebu.services.Kuca4Zida;
import rs.psi.beogradnawebu.services.MailService;
import rs.psi.beogradnawebu.services.Stan4Zida;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SchedulerScraper - Scheduled klasa koja je zaduzena da svakog dana u 23:59:59 pokrene skrejper i novopreporucene smestaje (one koji zadovoljavaju uslov) salje korisnicima koji su pretplatili na mejling servis
 * @version 1.0
 */
@EnableAsync
@Service
public class SchedulerScraper {

    private static final Logger log = LoggerFactory.getLogger(FilterController.class);
    private final Kuca4Zida kuca4Zida;
    private final Stan4Zida stan4Zida;

    private final KorisnikDAO korisnikDAO;

    private final MMLVRecommenderImpl mmlvRecommender;

    private final RecAlgDAO recAlgDAO;

    private final SmestajDAO smestajDAO;

    private final MailService mailService;

    /**
     * Kreiranje instance
     * @param kuca4Zida
     * @param stan4Zida
     * @param korisnikDAO
     * @param mmlvRecommender
     * @param recAlgDAO
     * @param smestajDAO
     * @param mailService
     */
    public SchedulerScraper(
            Kuca4Zida kuca4Zida,
            Stan4Zida stan4Zida,
            KorisnikDAO korisnikDAO,
            MMLVRecommenderImpl mmlvRecommender,
            RecAlgDAO recAlgDAO,
            SmestajDAO smestajDAO,
            MailService mailService
    ){
        this.mailService = mailService;
        this.smestajDAO = smestajDAO;
        this.recAlgDAO = recAlgDAO;
        this.mmlvRecommender = mmlvRecommender;
        this.korisnikDAO = korisnikDAO;
        this.kuca4Zida = kuca4Zida;
        this.stan4Zida = stan4Zida;
    }

    /**
     * Asinhrona metoda koja pokrece dve niti i 2 drajvera za skrejpovanje; novopreporucene smestaje salje korisnicima ako smestaj zadovolji uslov
     */
    @Async
    @Scheduled(cron = "59 59 23 * * *")
    public void scraperScheduler(){

        final List<List<Smestaj>> kucaList = new ArrayList<>();
        final List<List<Smestaj>> stanList = new ArrayList<>();

//       Thread threadkuca =  new Thread(new Runnable() {
//            @Override
//            public void run() {
//                log.info("Pokrenut thread threadkuca!");
//                kucaList.add(kuca4Zida.callScraper());
//            }
//        });

        Thread threadstan = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("Pokrenut thread scraper!");
                stanList.add(stan4Zida.callScraper());
                kucaList.add(kuca4Zida.callScraper());
            }
        });
//        threadkuca.start();
        threadstan.start();

        try{
//            threadkuca.join();
            threadstan.join();
        } catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
            log.info("NIJE USPELO SCRAPEOVANJE!");
            return;
        }
        log.info("USPELO SCRAPEOVANJE!");

        ConcurrentHashMap<String, List<String>> preporuke = new ConcurrentHashMap<>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Korisnik> korisniks = korisnikDAO.getByEpredlogFlag();
                for(Korisnik k:korisniks){
                    Recalgdata recalgdata = recAlgDAO.get((int)k.getIdkorisnik()).orElse(null);
                    if(recalgdata == null) continue;
                    SmestajDAO.AvgData avgData =smestajDAO.getAvgAcc(k.getIdkorisnik());
                    for(Smestaj s: stanList.get(0)){
                        if(mmlvRecommender.recommend(recalgdata,avgData ,s)){
                            List<String> list = preporuke.getOrDefault(k.getEmail(),new ArrayList<>());
                            list.add(s.toString() + "\n");
                            preporuke.put(k.getEmail(),list);

                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Korisnik> korisnikList = korisnikDAO.getByEpredlogFlag();
                for(Korisnik korisnik : korisnikList){
                    Recalgdata recalgdata = recAlgDAO.get((int)korisnik.getIdkorisnik()).orElse(null);
                    if(recalgdata == null) continue;
                    SmestajDAO.AvgData avgData = smestajDAO.getAvgAcc(korisnik.getIdkorisnik());
                    for(Smestaj smestaj : kucaList.get(0)){
                        if(mmlvRecommender.recommend(recalgdata,avgData,smestaj)){
                            List<String> localpreporuke = preporuke.getOrDefault(korisnik.getEmail(),new ArrayList<>());
                            localpreporuke.add(smestaj.toString()+"\n");
                            preporuke.put(korisnik.getEmail(),localpreporuke);
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            return;
        }
        log.info("ZAVRSIO PREPORUKE");
        String genericText = "Preporuceni stanovi:\n";
        for(String key : preporuke.keySet()){
            try{
                StringBuilder stringBuilder = new StringBuilder();
                for(String smestaj : preporuke.get(key)){
                    stringBuilder.append("----------------------------------------------------------------------\n");
                    stringBuilder.append(smestaj);
                }
                mailService.sendEmail(key,genericText,stringBuilder.toString());
            }
            catch (Exception e){
                log.info("NIJE VALIDAN EMAIL");
            }

        }
        log.info("ZAVRSIO SLANJE");
    }
}
