package com.example.customizing.csrf.protection.config;

import com.example.customizing.csrf.protection.csrf.CustomCsrfTokenRepository;
import com.example.customizing.csrf.protection.filters.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class ProjectConfig {

//    https://docs.spring.io/spring-security/reference/5.8/migration/servlet/exploits.html#_i_am_using_a_single_page_application_with_cookiecsrftokenrepository

    private final CustomCsrfTokenRepository customTokenRepository;

    private final CsrfCookieFilter csrfCookieFilter;

    public ProjectConfig(CustomCsrfTokenRepository customTokenRepository, CsrfCookieFilter csrfCookieFilter) {
        this.customTokenRepository = customTokenRepository;
        this.csrfCookieFilter = csrfCookieFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.csrf(c -> {
            c.ignoringRequestMatchers("/ciao");
            c.csrfTokenRepository(customTokenRepository);
            c.csrfTokenRequestHandler(requestHandler);
        }).addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.authorizeRequests(c -> c.anyRequest().permitAll());

        return http.build();

    }

}
