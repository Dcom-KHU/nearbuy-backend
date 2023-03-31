package dcom.nearbuybackend.api.global.image;

import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    public void uploadUserImage(HttpServletRequest httpServletRequest, MultipartFile image) throws IOException {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        if (image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지가 비어있습니다.");
        }

        String contentType = image.getContentType();
        String fileExtension;

        if (ObjectUtils.isEmpty(contentType)) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "미디어 타입을 알 수 없습니다.");
        }

        if (contentType.contains("image/jpeg")) {
            fileExtension = ".jpg";
        } else if (contentType.contains("image/png")) {
            fileExtension = ".png";
        } else if (contentType.contains("image/gif")) {
            fileExtension = ".gif";
        } else {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "jpg, png, gif만 업로드할 수 있습니다.");
        }

        String absolutePath = new File("").getAbsolutePath() + "/image/";
        String path = "user/" + user.getId() + fileExtension;

        File file = new File(absolutePath + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        image.transferTo(file);

        user.setImage(path);
        userRepository.save(user);
    }

    public void deleteUserImage(HttpServletRequest httpServletRequest) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        String path = user.getImage();
        if (path == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저는 이미지가 없습니다.");
        }

        String absolutePath = new File("").getAbsolutePath() + "/image/";
        File file = new File(absolutePath + path);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저의 이미지 경로가 잘못되었습니다.");
        }

        file.delete();
        user.setImage(null);
        userRepository.save(user);
    }
}
