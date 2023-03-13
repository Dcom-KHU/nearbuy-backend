package dcom.nearbuybackend.api.domain.user.controller;

import dcom.nearbuybackend.api.domain.user.dto.UserLoginRequestDto;
import dcom.nearbuybackend.api.domain.user.dto.UserLoginResponseDto;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.domain.user.service.UserLoginService;
import dcom.nearbuybackend.api.global.security.config.Token;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = {"User Login Controller"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @ApiOperation(value = "일반 회원가입", notes = "아이디(이메일), 이름(닉네임), 비밀번호, 사용자 집 위치를 입력받아 일반 회원가입을 진행합니다.")
    @ApiResponses ({
            @ApiResponse(code = 400, message = "이미 존재하는 아이디, 이름입니다.")
    })
    @PostMapping("/join")
    public ResponseEntity<Void> joinUser(@ApiParam(value = "일반 회원가입 정보", required = true) @RequestBody UserLoginRequestDto.UserJoin data) {
        userLoginService.joinUser(data);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "일반 로그인", notes = "아이디(이메일), 비밀번호를 입력받아 일반 로그인을 진행합니다.")
    @ApiResponses ({
            @ApiResponse(code = 400, message = "존재하지 않는 아이디입니다."),
            @ApiResponse(code = 401, message = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 아이디입니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto.UserLogin> loginUser(@ApiParam(value = "일반 로그인 정보", required = true) @RequestBody UserLoginRequestDto.UserLogin data) {

        if (userLoginService.loginUser(data)) {

            Token token = tokenService.generateToken(data.getId(),"USER");

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                    .maxAge(30 * 24 * 60 * 60) // 만료 기한
                    .path("/")
                    .secure(true)
                    .httpOnly(true)
                    .build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Set-Cookie", refreshTokenCookie.toString());

            userLoginService.storeRefreshToken(userRepository.findById(data.getId()).get(), token.getRefreshToken());

            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(UserLoginResponseDto.UserLogin.of(token.getAccessToken()));
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 로그인 플랫폼을 입력받아 해당하는 플랫폼의 소셜 로그인을 진행합니다.")
    @GetMapping("/login/social")
    public void socialLoginUser(HttpServletResponse httpServletResponse, @ApiParam(value = "소셜 로그인 플랫폼", required = true) @RequestParam String platform) throws IOException {
        httpServletResponse.sendRedirect("http://localhost:8080/oauth2/authorization/" + platform);
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "해당하는 아이디(이메일)로 임시 비밀번호를 담은 메일을 전송합니다.")
    @ApiResponses ({
            @ApiResponse(code = 404, message = "해당하는 유저의 이메일이나 이름이 없습니다.")
    })
    @PostMapping("find")
    public ResponseEntity<Void> findUserPassword(@ApiParam(value = "비밀번호 찾기 정보", required = true) @RequestBody UserLoginRequestDto.UserFindPassword data) {
        userLoginService.findPassword(data);
        return ResponseEntity.ok().build();
    }
}