package dcom.nearbuybackend.api.domain.user.service;

import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.dto.UserRequestDto;
import dcom.nearbuybackend.api.domain.user.dto.UserResponseDto;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    // 유저 페이지 조회
    public UserResponseDto.UserPageInfo getUserPage(String id) {
        User user = userRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."));

        return UserResponseDto.UserPageInfo.of(user);
    }

    // 유저 페이지 수정
    public void modifyUserPage(HttpServletRequest httpServletRequest, String id, UserRequestDto.UserPageModify data) {
        User user1 =  tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        User user2 = userRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."));

        if(user1.equals(user2)) {
            user2.setName(data.getName());
            user2.setImage(data.getImage());
            user2.setLocation(data.getLocation());

            userRepository.save(user2);
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"유저 페이지 수정 접근 권한이 없습니다.");
    }

    // 유저 비밀번호 변경
    public void changeUserPassword(HttpServletRequest httpServletRequest, UserRequestDto.UserChangePassword data) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        if (user.getSocial().equals(false)) {
            if (user.getPassword().equals(data.getPassword())) {
                if (data.getNewPassword().equals(data.getNewPasswordCheck())) {
                    user.setPassword(data.getNewPassword());

                    userRepository.save(user);
                } else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새로운 비밀번호와 새로운 비밀번호 확인이 일치하지 않습니다.");
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다.");
        }
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "소셜 로그인은 비밀번호 변경을 지원하지 않습니다.");
    }
}
