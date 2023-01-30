package dcom.nearbuybackend.api.domain.user.service;

import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.dto.UserRequestDto;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 일반 회원 가입
    public void joinUser(UserRequestDto.UserJoin data) {
        Optional<User> duplicateId = userRepository.findById(data.getId());
        Optional<User> duplicateName = userRepository.findByName(data.getName());

        if (duplicateId.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 존재하는 아이디입니다.");
        else if (duplicateName.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 존재하는 이름입니다.");
        else {
            User user = User.builder()
                    .id(data.getId())
                    .name(data.getName())
                    .password(data.getPassword())
                    .mannerPoint(36.5)
                    .build();

            userRepository.save(user);
        }
    }

    // 일반 로그인
    public Boolean loginUser(UserRequestDto.UserLogin data) {
        User user = userRepository.findById(data.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이디 입니다"));

        if(!user.getPassword().equals(data.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }
        else {
            return true;
        }
    }

    // refreshToken DB에 저장
    public void storeRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }
}
