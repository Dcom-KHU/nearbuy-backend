package dcom.nearbuybackend.api.global.image;

import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.repository.PostRepository;
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
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final TokenService tokenService;

    private final String absolutePath = new File("").getAbsolutePath() + "/image/";

    public void uploadUserImage(HttpServletRequest httpServletRequest, MultipartFile image) throws IOException {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        if (image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지가 비어있습니다.");
        }

        String path = "user/" + user.getId() + getFileExtension(image);

        File file = new File(absolutePath + path);
        file.mkdirs();
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

        File file = new File(absolutePath + path);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저의 이미지 경로가 잘못되었습니다.");
        }

        file.delete();
        user.setImage(null);
        userRepository.save(user);
    }

    public void uploadPostImage(HttpServletRequest httpServletRequest, Integer id, List<MultipartFile> images) throws IOException {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다."));

        if (!user.equals(post.getWriter())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 접근 권한이 없습니다.");
        }
        if (images.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지가 비어있습니다.");
        }

        List<String> existingImage = post.getImageList();

        for (MultipartFile image : images) {
            String path = "post-" + post.getId() + "/" + System.currentTimeMillis() + '-' + System.nanoTime() + getFileExtension(image);

            File file = new File(absolutePath + path);
            file.mkdirs();
            image.transferTo(file);

            existingImage.add(path);
        }
        post.setImage(String.join(",", existingImage));
        postRepository.save(post);
    }

    public void deletePostImage(HttpServletRequest httpServletRequest, Integer id, List<Integer> index) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다."));

        if (!user.equals(post.getWriter())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 접근 권한이 없습니다.");
        }

        List<String> existingImage = post.getImageList();

        index.sort(Collections.reverseOrder());

        for (int i : index) {
            try {
                File file = new File(absolutePath + existingImage.get(i));
                if (!file.exists()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "저장된 이미지 경로가 잘못되었습니다.");
                }

                file.delete();
                existingImage.remove(i);
            } catch (IndexOutOfBoundsException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "index가 범위를 벗어났습니다.");
            }
        }
        String result = String.join(",", existingImage);
        if (existingImage.isEmpty()) {
            result = null;
        }
        post.setImage(result);
        postRepository.save(post);
    }

    private String getFileExtension(MultipartFile image) {
        String contentType = image.getContentType();

        if (ObjectUtils.isEmpty(contentType)) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "미디어 타입을 알 수 없습니다.");
        }

        if (contentType.contains("image/jpeg")) {
            return ".jpg";
        }
        if (contentType.contains("image/png")) {
            return ".png";
        }
        if (contentType.contains("image/gif")) {
            return ".gif";
        }
        throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "jpg, png, gif만 업로드할 수 있습니다.");
    }
}
