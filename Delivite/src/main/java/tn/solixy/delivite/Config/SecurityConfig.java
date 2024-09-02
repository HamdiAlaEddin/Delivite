package tn.solixy.delivite.Config;
import org.springframework.security.config.http.SessionCreationPolicy;
import tn.solixy.delivite.Auth.JwtFilter;
import tn.solixy.delivite.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/Delivite/addUser", "/Delivite/getbytoken","/Delivite/addRestaurant", "/Delivite/authenticate_user", "/Delivite/resetpassword", "/Delivite/resetpasswordrequest").permitAll() // Ces chemins sont accessibles à tous
                        .requestMatchers("/Delivite/admin/**", "/admin/**").hasRole("ADMIN") // Chemins spécifiques au rôle ADMIN
                        .requestMatchers("/Delivite/user/**", "/user/**").hasAnyRole("RESTO", "CLIENT", "CHAUFFEUR")
                        .requestMatchers("/Delivite/getbytoken").permitAll()
                       // .requestMatchers("/Delivite/admin/accept-chauffeur/**").permitAll()
                        .requestMatchers("/Delivite//getAllLivraison").permitAll()
                        //       .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                        .anyRequest().permitAll()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/login") // Page d'erreur pour accès refusé
                );

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();}
}