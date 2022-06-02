/**
 * Marko Mirkovic 2019/0197
 */
package rs.psi.beogradnawebu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.psi.beogradnawebu.services.PodaciKorisnikaServis;

/**
 * SecurityConfig - klasa za konfiguraciju bezbednosti
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JdbcTemplate jdbcTemplate;
    private PodaciKorisnikaServis pks;

    public SecurityConfig(JdbcTemplate jdbcTemplate, PodaciKorisnikaServis pks) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.pks = pks;
    }

    /**
     * authenticationProvider - podesavanje userDetails servisa i enkodera sifre
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(pks);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    /**
     * configure - podesavanje userDetails servisa i enkodera sifre
     * @param amb the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb.authenticationProvider(authenticationProvider());
                //.jdbcAuthentication()
                //.dataSource(jdbcTemplate.getDataSource());
    }

    /**
     * configure - podesavanje dozvola poziva i stranice logina i logauta
     * @param httpSec the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
                httpSec
                        .authorizeRequests()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/logout").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/like/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/unlike/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/isliked/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/komentarLike/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/unlikeKomentar/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/islikedKomentar/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/**").permitAll()
                        .and().formLogin().loginPage("/login").failureForwardUrl("/?error")
                        .and().logout().logoutSuccessUrl("/")
                        .and().csrf().disable().cors();
    }

    /**
     * getPasswordEncoder - podesavanje enkodera sifre
     * @return
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}