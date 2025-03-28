package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.security.CustomLoginSuccessHandler;
import com.example.demo.security.MyUserDetailsService;

// import ...

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Ovo definiše kako će Spring Security "presretati" HTTP zahteve (filter chain)
     * i koje rute su dozvoljene/anonymus, a koje zahtevaju prijavu i/ili ulogu.
     */
	
	@Bean
	public CustomLoginSuccessHandler customLoginSuccessHandler(KorisnikRepository kr) {
	    return new CustomLoginSuccessHandler(kr);
	}

	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.authorizeHttpRequests(auth -> auth
                // 1) Javni resursi i stranice koje NE zahtevaju login
                .requestMatchers("/", "/pretraga", "/pretragaPoZanru",
                                 "/tekstPesme/**", "/tekst/**").permitAll()
                .requestMatchers("/registracija", "/processRegistracija").permitAll()
                .requestMatchers("/prijava", "/processPrijava").permitAll()
                // 2) Stranice koje zahtevaju da korisnik bude logovan (bilo koja uloga)
                .requestMatchers("/dodajTekst", "/mojNalog", "/oceniTekst",
                                 "/dodajKomentar", "/dodajUOmiljene", "/ukloniIzOmiljenih",
                                 "/profil/**", "/brisanjeTeksta", "/processBrisanjeTeksta")
                    .authenticated()
                // 3) Samo ADMIN može verifikovati
                .requestMatchers("/verifikacija", "/processVerifikacija", "/admin/report/**").hasRole("ADMIN")
                // Sve ostalo? Slobodno ili zaključano? Najčešće .permitAll() ili .authenticated()
                .anyRequest().permitAll()
            )

            // 2) Opciono: definisanje login forme
            .formLogin(login -> login
                .loginPage("/prijava")         // putanja do JSP strane za login
                .loginProcessingUrl("/processPrijava")
                .usernameParameter("email")    // polje name="email" u formi
                .passwordParameter("lozinka")  // polje name="lozinka" u formi
                .successHandler(customLoginSuccessHandler(null))  // posle uspesnog logina baci na "/"
                .failureUrl("/prijava?error=NeuspesnaPrijava")
                .permitAll()
            )

            // 3) Ako ipak hoćete Basic Auth (može i *paralelno* ali obično nije praksa)
            //.httpBasic(Customizer.withDefaults())

            // 4) Logout config
            .logout(logout -> logout
                .logoutUrl("/logout")      
                .logoutSuccessUrl("/")     
                .permitAll()
            )

            // 5) CSRF - za JSP forme obično ga ostavljamo uključenog
            //   ali ako dobijate 403, probajte csrf.disable().
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

//    private AuthenticationSuccessHandler customLoginSuccessHandler() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
     * Ako planirate da koristite password hashing (BCrypt), definisite ovde.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Vrati instancu AuthenticationManager-a koji koristi
     * nas "custom" UserDetailsService i passwordEncoder.
     */
    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            MyUserDetailsService userDetailsService
    ) throws Exception {
        return http
            .getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)  // klasa koju cemo kreirati
            .passwordEncoder(passwordEncoder)
            .and()
            .build();
    }
}
