/**
 * Marko Mirkovic 2019/0197
 * Veljko Lazic 2019/0241
 */

package rs.psi.beogradnawebu;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer - klasa za podesavanje servleta
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * configure - konfiguracija aplikacije
     * @param application a builder for the application context
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BeogradNaWebuApplication.class);
    }

}
