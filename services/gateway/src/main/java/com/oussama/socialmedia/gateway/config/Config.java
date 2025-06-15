package com.oussama.socialmedia.gateway.config;

import com.oussama.socialmedia.gateway.filter.JWTAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@AllArgsConstructor
public class Config {
    private JWTAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service",r-> r.path("/api/v1/users/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://user-service")
                )
                .route("chat-service",r-> r.path("/api/v1/chat/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://chat-service")

                )
                .route("auth-service",r -> r.path("/api/v1/auth/**")
                        .uri("lb://auth-service")
                )
                .route("media-service",r -> r.path("/api/v1/media/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://media-service")
                )
                .build();
    }
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOriginPattern("*");// your React app
        corsConfig.setMaxAge(8000L);
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }



}
