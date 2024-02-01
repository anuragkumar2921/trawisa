package com.backend.trawisa.config;


import com.app.base.project.constant.BaseFinalConstant;
import com.app.base.project.utils.InvalidUrlFilter;
import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import com.backend.trawisa.security.jwt.JwtAuthEntryPoint;
import com.backend.trawisa.security.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.app.base.project.utils.Utils.*;
import static com.backend.trawisa.constant.ApiConstant.*;


@Configuration
public class ApiSecurityConfig {

    private final JwtAuthEntryPoint point;
    private final JwtAuthFilter filter;
    private final MultiLangMessage langMessage;
    private final CorsConfiguration corsConfiguration;

    public ApiSecurityConfig(JwtAuthEntryPoint point, JwtAuthFilter filter, MultiLangMessage langMessage, CorsConfiguration corsConfiguration) {
        this.point = point;
        this.filter = filter;
        this.langMessage = langMessage;
        this.corsConfiguration = corsConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfiguration); // Apply CORS to all endpoints
                    cors.configurationSource(source);
                })
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HOME + "**").authenticated()
                                .requestMatchers(PROFILE + "**").authenticated()
                                .requestMatchers(VENUE + "**").authenticated()
                                .requestMatchers(TEAMS + "**").authenticated()
                                .requestMatchers(SETTING + "**").authenticated()
                                .requestMatchers(HOME + "**").authenticated()
                                .requestMatchers(AUTH + "**").permitAll()

                                .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(new InvalidUrlFilter(langMessage), BasicAuthenticationFilter.class);
        httpSecurity.exceptionHandling((exception) -> exception.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler()));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            Print.log("authenticationEntryPoint " + authException.getMessage());
            checkLanguageHeader(response, request);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(BaseFinalConstant.API_HEADER.Content_TYPE_JSON);
            WriteErrorResponse(response, authException.getMessage());

        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            Print.log("accessDeniedHandler " + accessDeniedException.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            WriteErrorResponse(response, langMessage.getLocalizeMessage("somethingWentWrong"));
        };
    }


}


