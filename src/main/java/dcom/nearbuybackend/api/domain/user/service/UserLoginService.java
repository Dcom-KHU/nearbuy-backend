package dcom.nearbuybackend.api.domain.user.service;

import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.dto.UserLoginRequestDto;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    // 일반 회원 가입
    public void joinUser(UserLoginRequestDto.UserJoin data) {
        Optional<User> duplicateId = userRepository.findById(data.getId());
        Optional<User> duplicateName = userRepository.findByName(data.getName());

        if (duplicateId.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        else if (duplicateName.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이름입니다.");
        else {
            User user = User.builder()
                    .id(data.getId())
                    .name(data.getName())
                    .password(data.getPassword())
                    .mannerPoint(36.5)
                    .social(false)
                    .build();

            userRepository.save(user);
        }
    }

    // 일반 로그인
    public Boolean loginUser(UserLoginRequestDto.UserLogin data) {
        User user = userRepository.findById(data.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이디 입니다"));

        if (!user.getPassword().equals(data.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        } else {
            return true;
        }
    }

    // refreshToken DB에 저장
    public void storeRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    // 비밀번호 찾기
    public void findPassword(UserLoginRequestDto.UserFindPassword data) {
        String id = data.getId();
        String name = data.getName();

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다"));

        if (user.getId().equals(id) && user.getName().equals(name)) {
            String tempPassword = getTempPassword();
            user.setPassword(tempPassword);

            String email = "sw.choi@khu.ac.kr";
            //        String email = user.getId();
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Nearbuy 임시비밀번호 안내 이메일 입니다");
            simpleMailMessage.setText("안녕하세요. Nearbuy 임시비밀번호 안내 관련 이메일 입니다.\n" +
                    "회원님의 임시 비밀번호는 " + tempPassword + "입니다. 로그인 후에 비밀번호를 변경 해주세요.");
            userRepository.save(user);
            javaMailSender.send(simpleMailMessage);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 이메일이나 이름이 없습니다.");
        }

    }
    private String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
