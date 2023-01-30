package dcom.nearbuybackend.api.global.security.config;

import dcom.nearbuybackend.api.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final TokenService tokenService;

    // Token 인증 정보를 SecurityContext 안에 저장
    // JwtAuthFilter 통과시 데이터베이스를 거치지 않고 User의 아이디와 역할(권한)만 Security Context 안에 저장
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Header로부터 Token 추출
        String token = tokenService.resolveToken((HttpServletRequest) request);

        // 유효성 검증
        if (token != null && tokenService.verifyToken(token)) {
            // Token에서 User의 아이디와 역할(권한)을 빼서 Security User 객체를 만들어 Authentication 객체 반환
            User user = tokenService.getUserByToken(token);
            Authentication auth = getAuthentication(user);
            // 해당 Security User를 Security Context 안에 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    // Token에서 역할(권한)을 빼서 Security User 객체를 만들어 Authentication 객체 반환
    public Authentication getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, "",
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
