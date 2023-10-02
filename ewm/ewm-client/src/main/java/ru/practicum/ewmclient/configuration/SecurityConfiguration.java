package ru.practicum.ewmclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${super_admin_username}")
    private String username;

    @Value("${super_admin_password}")
    private String password;

    private final String[] publicUri = {"/categories", "/categories/**", "/compilations", "/compilations/**",
            "/events", "/events/**"};

    private final String[] privateUri = {"/users/**"};

    private final String[] adminUri = {"/admin/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(publicUri)
                .permitAll()
                .antMatchers(adminUri)
                .hasRole("admin")
                .antMatchers(privateUri)
                .hasRole("user")
                .and()
                .formLogin()
                .and()
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.builder()
                        .username(username)
                        .password("{base64}"+password)
                        .roles("admin")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }


}