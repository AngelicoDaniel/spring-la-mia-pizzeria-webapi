package org.lessons.springpizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    /* per definire un AuthenticationProvider ho bisogno di:
   - uno UserDetailsService
   - un PasswordEncoder
  */
    // questo è lo UserDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // questo è il PasswordEncoder (che deduce l'algoritmo di encoding da una stringa nella password
    // stessa)
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        // gli setto lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        return provider;

    }

    /*
* /offer solo ADMIN
* /pizzas, /pizzas/{id} ADMIN e USER
* /pizzas/edit/** ADMIN
* /pizzas/create ADMIN
 /ingredients/** ADMIN
*/
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll() //per far funzionare le API
                .requestMatchers(HttpMethod.POST).hasAuthority("ADMIN")
                .requestMatchers("/pizzas/create").hasAuthority("ADMIN")
                .requestMatchers("/pizzas/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/ingredients").hasAuthority("ADMIN")
                .requestMatchers("/offers/**").hasAuthority("ADMIN")
                .requestMatchers("/pizzas").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/pizzas/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(("/**")).permitAll()
                .and().formLogin()
                .and().logout();


        http.csrf().disable();
        return http.build();
    }
}
