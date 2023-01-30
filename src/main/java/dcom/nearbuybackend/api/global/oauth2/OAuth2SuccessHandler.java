package dcom.nearbuybackend.api.global.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.dto.UserResponseDto;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.domain.user.service.UserService;
import dcom.nearbuybackend.api.global.security.config.Token;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Optional<User> userOptional = userRepository.findById(email);

        // Token 생성
        Token token = tokenService.generateToken(email, "USER");

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .maxAge(30 * 24 * 60 * 60) // 30일
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build();

        // 데이터베이스에 User 정보 저장
        User user= userOptional.orElseGet(() -> userRepository.save(
                User.builder()
                        .id(email)
                        .name((String) oAuth2User.getAttribute("name"))
                        .refreshToken(refreshTokenCookie.toString())
                        .build()
        ));

        // refreshToken 갱신
        userService.storeRefreshToken(user,token.getRefreshToken());

        // Response Header에 refreshToken 생성
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // Response Body에 accessToken 생성
        UserResponseDto.UserLogin userLogin = new UserResponseDto.UserLogin();
        userLogin.setAccessToken(token.getAccessToken());
        String accessToken = objectMapper.writeValueAsString(userLogin);
        response.getWriter().write(accessToken);

        // 로그인 후 메인 페이지로 Redirect
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000");
    }
}
