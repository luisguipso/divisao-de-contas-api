package gomes.luis.divisaodecontas.security;

import gomes.luis.divisaodecontas.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http.securityMatcher(new AntPathRequestMatcher("/api/**"));
        http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.cors(cors -> cors.configure(http));
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS));
        http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
        http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/login/**")
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(cors -> cors.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
