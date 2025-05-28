package com.oussama.socialmedia.gateway.filter;

import com.oussama.socialmedia.gateway.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;


@Component
@AllArgsConstructor
public class JWTAuthenticationFilter implements GatewayFilter {
    private Utils utils;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        final List<String> apiEndpoints = List.of("/api/v1/auth/login", "/api/v1/auth/register","/eureka");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream().noneMatch(uri -> r.getURI().getPath().contains(uri));
        if (request.getMethod().name().equals("OPTIONS")) {
            return chain.filter(exchange);
        }
        if(isApiSecured.test(request)) {
            String Authorization = request.getHeaders().getFirst("Authorization");

            if(Authorization != null && Authorization.startsWith("Bearer ")) {
                String token = Authorization.substring(7);

                    if(utils.isTokenValid(token)){
                        String username = utils.getUserNameFromToken(token);
                        ServerHttpRequest mutatedRequest = exchange.getRequest()
                                .mutate()
                                .header("username", username)
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange);
                    }
            }
            if(Authorization == null && request.getURI().getQuery()!=null && request.getURI().getQuery().contains("token=")){
                String token = request.getURI().getQuery().split("token=")[1];
                if(utils.isTokenValid(token)){
                    return chain.filter(exchange);
                }
            }
        }
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
