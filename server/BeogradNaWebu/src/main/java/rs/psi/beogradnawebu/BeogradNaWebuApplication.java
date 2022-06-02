package rs.psi.beogradnawebu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@SpringBootApplication
@EnableScheduling
public class BeogradNaWebuApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeogradNaWebuApplication.class, args);
    }

}
