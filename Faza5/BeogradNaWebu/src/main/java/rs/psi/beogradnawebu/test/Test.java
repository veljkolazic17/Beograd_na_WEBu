package rs.psi.beogradnawebu.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.psi.beogradnawebu.controller.FilterController;
import rs.psi.beogradnawebu.dao.KorisnikDAO;
import rs.psi.beogradnawebu.dao.RecAlgDAO;
import rs.psi.beogradnawebu.dao.SmestajDAO;
import rs.psi.beogradnawebu.model.Korisnik;
import rs.psi.beogradnawebu.model.Recalgdata;
import rs.psi.beogradnawebu.model.Smestaj;
import rs.psi.beogradnawebu.recalg.MMLVRecommenderImpl;
import rs.psi.beogradnawebu.services.MailService;
import rs.psi.beogradnawebu.services.SimplePasswordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/test")
public class Test {

    private static final Logger log = LoggerFactory.getLogger(FilterController.class);

    private final KorisnikDAO korisnikDAO;

    private final MMLVRecommenderImpl mmlvRecommender;

    private final RecAlgDAO recAlgDAO;

    private final SmestajDAO smestajDAO;

    private final MailService mailService;
    private final SimplePasswordGenerator simplePasswordGenerator;

    public Test(KorisnikDAO korisnikDAO, MMLVRecommenderImpl mmlvRecommender, RecAlgDAO recAlgDAO, SmestajDAO smestajDAO, MailService mailService, SimplePasswordGenerator simplePasswordGenerator) {
        this.korisnikDAO = korisnikDAO;
        this.mmlvRecommender = mmlvRecommender;
        this.recAlgDAO = recAlgDAO;
        this.smestajDAO = smestajDAO;
        this.mailService = mailService;
        this.simplePasswordGenerator = simplePasswordGenerator;
    }

    @GetMapping("/test1")
    public String test1(){
        Korisnik korisnik = korisnikDAO.getUserByUsername("maga").orElse(null);
        System.out.println(korisnik.getEmail() + " " + korisnik.getIdkorisnik());
        return "test";
    }

    @GetMapping("/send")
    public void send_email(){
        mailService.sendEmail("veljkolazic117@gmail.com","Provera email servisa","EmailServisRadi");
    }

    @GetMapping("/password")
    public String passowrd(){
        return simplePasswordGenerator.generatePassword();
    }

    @GetMapping("/scheduledmail")
    public void mail(){

        final List<List<Smestaj>> kucaList = new ArrayList<>();
        final List<List<Smestaj>> stanList = new ArrayList<>();

        kucaList.add(smestajDAO.list());
        stanList.add(smestajDAO.list());


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
