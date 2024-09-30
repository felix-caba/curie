package org.cosm.curie.configs;

import org.cosm.curie.servicios.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    @Profile("!dev")
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(form->form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login-error")

                )

                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                )

                .authorizeHttpRequests(
                        auth->auth
                                .requestMatchers("/css/**", "/js/**", "/register", "/login").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                .requestMatchers("/node_modules/**").permitAll()

                                .requestMatchers("/register", "/login", "/productos").permitAll()

                                .requestMatchers("/forgot-password", "/reset-password").permitAll()

                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/usuarios/current").hasAnyRole( "USER")
                                .requestMatchers(HttpMethod.PUT, "/api/usuarios/updateCurrent").hasAnyRole( "USER")
                                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")


                                .anyRequest().authenticated())



                .httpBasic(withDefaults())

                .userDetailsService(customUserDetailsService)

                .rememberMe(rememberMe->rememberMe
                        .key("remember-me-key")
                        .tokenValiditySeconds(2592000) // 30 días
                        .useSecureCookie(true)
                        .rememberMeParameter("remember-me")
                )

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expired")
                )

                .build();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(new DevAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    @Profile("dev")
    public UserDetailsService devUserDetailsService() {
        UserDetails adminUser = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("felix")
                .roles("ADMIN")
                .authorities("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(adminUser);
    }

}