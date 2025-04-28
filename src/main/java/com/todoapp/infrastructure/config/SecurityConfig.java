package com.todoapp.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.todoapp.infrastructure.exceptions.AuthEntryPoint;
import com.todoapp.infrastructure.handlers.OAuth2SuccessHandler;
import com.todoapp.infrastructure.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private static final String REGISTER = "/api/auth/register";
        private static final String REFRESH_TOKEN = "/api/auth/refreshToken";
        private static final String AUTHENTICATE = "/api/auth/login";
        private static final String OAUTH = "/oauth2/**";
        private static final String LOGIN = "/login";

        @Autowired
        private EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private AuthEntryPoint authEntryPoint;



        // ! daha önce jwt authentication filter da tanımladığımız her istekte jwt yi
        //! kontrol edecek metodu burda hangi
        // ! istekler için olacağını ayarlıyoruz.Session ayarlama
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, ClientRegistrationRepository repo,
                        OAuth2SuccessHandler successHandler) throws Exception {

                http.csrf().disable()
                                .authorizeRequests(request -> request
                                                // .expressionHandler(webSecurityExpressionHandler())
                                                .requestMatchers(REGISTER, AUTHENTICATE, REFRESH_TOKEN, OAUTH, LOGIN)
                                                .permitAll()
                                                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                                .requestMatchers("/api/user/**").hasAuthority("USER")
                                                .anyRequest().authenticated())
                                .exceptionHandling()
                                .authenticationEntryPoint(authEntryPoint).and()
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(emailPasswordAuthenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .oauth2Login(oauth2 -> oauth2
                                                .successHandler(successHandler)
                                                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                                                .authorizationRequestResolver(
                                                                                customAuthorizationRequestResolver(
                                                                                                repo))
                                                                .authorizationRequestRepository(
                                                                                new HttpSessionOAuth2AuthorizationRequestRepository()))

                                );

                return http.build();

        }

        private OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver(
                        ClientRegistrationRepository repo) {
                DefaultOAuth2AuthorizationRequestResolver defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                                repo, "/oauth2/authorization");

                return new OAuth2AuthorizationRequestResolver() {
                        @Override
                        public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                                OAuth2AuthorizationRequest original = defaultResolver.resolve(request);
                                if (original == null)
                                        return null;

                                Map<String, Object> params = new HashMap<>(original.getAdditionalParameters());
                                params.put("prompt", "select_account");

                                return OAuth2AuthorizationRequest.from(original)
                                                .additionalParameters(params)
                                                .build();
                        }

                        @Override
                        public OAuth2AuthorizationRequest resolve(HttpServletRequest request,
                                        String clientRegistrationId) {
                                // TODO Auto-generated method stub
                                throw new UnsupportedOperationException("Unimplemented method 'resolve'");
                        }
                };
        }

}
