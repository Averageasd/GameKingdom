package org.example.storemanagementbestpractice.config;

import org.example.storemanagementbestpractice.handler.RestAuthFailureHandler;
import org.example.storemanagementbestpractice.handler.RestAutheSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests ->
                                requests
                                        .requestMatchers("/product").authenticated()
                                        .requestMatchers("/category").authenticated()
                                        .requestMatchers("/login").permitAll()
                )
                .formLogin(
                        form ->
                                form
                                        .loginProcessingUrl("/login")
                                        .permitAll()
                                        .successHandler(new RestAutheSuccessHandler())
                                        .failureHandler(new RestAuthFailureHandler())
                )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/logout")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .roles("USER")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("54321")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}

