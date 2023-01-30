package dcom.nearbuybackend.api.global.security.config;

import dcom.nearbuybackend.api.global.oauth2.CustomOAuth2UserService;
import dcom.nearbuybackend.api.global.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import static java.util.List.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

     private final CustomOAuth2UserService oAuth2UserService;
     private final OAuth2SuccessHandler successHandler;
     private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic().disable()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(of("http://localhost:3000","http://localhost:8080")); // 허용 Site
                    cors.setAllowedMethods(of("GET","POST", "PATCH", "DELETE")); // 허용 Method
                    cors.setAllowedHeaders(of("*")); // 허용 Header
                    cors.setAllowCredentials(true); // 자격 증명 요청 여부
                    return cors;
                })
                .and()
                .csrf().disable()  // Jwt 사용 시 인증 정보를 보관하지 않는 stateless 하기 때문에 Csrf(위조 요청) Disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Jwt를 사용하기 때문에 Spring Security가 Session을 생성하지도 않고 사용하지도 않음
                .and()
                .authorizeRequests() // Security 처리에 HttpServletRequest 이용
                .anyRequest().permitAll() // 모든 Request 허용 (임시 구현)

                .and()
                .addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login() // OAuth2 Login 적용
                .successHandler(successHandler) // 성공시의 Handler 적용
                .userInfoEndpoint().userService(oAuth2UserService); // OAuth2 User Service 적용

        return http.build();
    }
}