package org.example.storemanagementbestpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    //    @Bean
//    public SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests ->
                                requests
                                        .anyRequest()
                                        .permitAll())
                .formLogin(
                        form
                                -> form
                                .defaultSuccessUrl("/product", true)
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}

