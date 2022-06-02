/**
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */
package rs.psi.beogradnawebu.scheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rs.psi.beogradnawebu.services.Kuca4Zida;
import rs.psi.beogradnawebu.services.Stan4Zida;

/**
 * SchedulerScraper - Scheduled klasa koja je zaduzena da svakog dana u 23:59:59 pokrene skrejper i novopreporucene smestaje (one koji zadovoljavaju uslov) salje korisnicima koji su pretplatili na mejling servis
 * @version 1.0
 */
@EnableAsync
@Service
public class SchedulerScraper {

    private final Kuca4Zida kuca4Zida;
    private final Stan4Zida stan4Zida;

    /**
     * Kreiranje instance
     * @param kuca4Zida
     * @param stan4Zida
     */
    public SchedulerScraper(Kuca4Zida kuca4Zida,Stan4Zida stan4Zida){
        this.kuca4Zida = kuca4Zida;
        this.stan4Zida = stan4Zida;
    }

    /**
     * Asinhrona metoda koja pokrece dve niti i 2 drajvera za skrejpovanje; novopreporucene smestaje salje korisnicima ako smestaj zadovolji uslov
     */
    @Async
    @Scheduled(cron = "59 59 23 * * *")
    public void scraperScheduler(){
       Thread threadkuca =  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Pokrenut thread threadkuca!");
                kuca4Zida.callScraper();
            }
        });

        Thread threadstan = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Pokrenut thread threadstan!");
                stan4Zida.callScraper();
            }
        });
        threadkuca.start();
        threadstan.start();

        try{
            threadkuca.join();
            threadstan.join();
        } catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
            System.out.println("NIJE USPELO SCRAPEOVANJE!");
        }
        System.out.println("USPELO SCRAPEOVANJE!");
    }
}
