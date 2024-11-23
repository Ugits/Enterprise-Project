package org.jonas.enterpriseproject.config.security;

import org.jonas.enterpriseproject.auth.jwt.JwtFilter;
import org.jonas.enterpriseproject.auth.jwt.RestAccessDeniedHandler;
import org.jonas.enterpriseproject.auth.jwt.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtFilter jwtFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder,
                             CustomUserDetailsService customUserDetailsService,
                             CorsConfigurationSource corsConfigurationSource,
                             JwtFilter jwtFilter,
                             RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                             RestAccessDeniedHandler restAccessDeniedHandler) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtFilter = jwtFilter;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Enable CORS with the specified configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // Disable CSRF as we're using JWTs
                .csrf(AbstractHttpConfigurer::disable)

                // Set session management to stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/auth/**").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().authenticated()
                )

                // Configure exception handling
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )

                // Add JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // Set the authentication provider
                .authenticationProvider(authenticationProvider())

                // Configure logout behavior
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
