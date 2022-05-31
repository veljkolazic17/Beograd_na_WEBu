package rs.psi.beogradnawebu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeogradNaWebuApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeogradNaWebuApplication.class, args);
    }

}
