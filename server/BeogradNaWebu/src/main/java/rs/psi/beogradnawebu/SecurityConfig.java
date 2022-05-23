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

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(pks);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb.authenticationProvider(authenticationProvider());
                //.jdbcAuthentication()
                //.dataSource(jdbcTemplate.getDataSource());
    }

    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
                httpSec
                        .authorizeRequests()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/logout").hasAnyRole("KORISNIK", "ADMIN")
//                        .antMatchers("/like/**").hasAnyRole("KORISNIK", "ADMIN")
//                        .antMatchers("/unlike/**").hasAnyRole("KORISNIK", "ADMIN")
//                        .antMatchers("/isliked/**").hasAnyRole("KORISNIK", "ADMIN")
                        .antMatchers("/**").permitAll()
                        .and().formLogin().loginPage("/login").failureForwardUrl("/?error")
                        .and().logout().logoutSuccessUrl("/").and().csrf().disable().cors();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}