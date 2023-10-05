package com.Winterhold.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService)
            throws Exception
    {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        SecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        httpSecurity.authorizeHttpRequests((request) -> request
                        .requestMatchers("/home/index").hasAnyAuthority("admin")
                        .requestMatchers("/resources/**","account/loginForm","/account/accessDenied").permitAll()
                        .requestMatchers("/category/**",
                                "/customer/**",
                                "/book/**",
                                "/author/**",
                                "/loan/**").hasAuthority("admin")
                        .anyRequest().authenticated()
        ).formLogin((form)->form
                        .loginPage("/account/loginForm")
                        .loginProcessingUrl("/loginPost")
                        .defaultSuccessUrl("/home/index")
                        .failureHandler(authenticationFailureHandler())
        ).sessionManagement((session) -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
        ).logout((logout)->logout.permitAll()
        ).exceptionHandling((ex)->ex.accessDeniedPage("/account/accessDenied")
        ).cors((cors)->{});

        return httpSecurity.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }




    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

}
