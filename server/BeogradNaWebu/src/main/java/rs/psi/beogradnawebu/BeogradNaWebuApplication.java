/**
 * Marko Mirkovic 2019/0197
 * Jelena Lucic 2019/0268
 * Matija Milosevic 2019/0156
 * Veljko Lazic 2019/0241
 */

package rs.psi.beogradnawebu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Klasa zaduzena za pokretanje aplikacije
 * @version 1.0
 */
@ServletComponentScan
@SpringBootApplication
@EnableScheduling
public class BeogradNaWebuApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeogradNaWebuApplication.class, args);
    }

}
