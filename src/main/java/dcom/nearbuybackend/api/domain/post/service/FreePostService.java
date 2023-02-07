package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.FreePost;
import dcom.nearbuybackend.api.domain.post.dto.FreePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.FreePostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.FreePostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreePostService {

    private final FreePostRepository freePostRepository;
    private final TokenService tokenService;

    /**
     * 나눔 게시글 조회
     */
    public FreePostResponseDto.FreePostInfo getFreePost(Integer id) {
        FreePost freePost = freePostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        return FreePostResponseDto.FreePostInfo.of(freePost);
    }

    /**
     * 나눔 게시글 등록
     */
    public void registerFreePost(HttpServletRequest httpServletRequest, FreePostRequestDto.FreePostRegister post) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        FreePost freePost = new FreePost();
        freePost.setTitle(post.getTitle());
        freePost.setWriter(user);
        freePost.setDetail(post.getDetail());
        freePost.setImage(getJoinByComma(post.getImage()));
        freePost.setTime(System.currentTimeMillis());
        freePost.setLocation(post.getLocation());
        freePost.setOngoing(true);
        freePost.setTag(getJoinByComma(post.getTag()));

        freePostRepository.save(freePost);
    }

    private static String getJoinByComma(List<String> list) {
        return String.join(",", list);
    }

    /**
     * 나눔 게시글 수정
     */
    public void modifyFreePost(HttpServletRequest httpServletRequest, Integer id,
                               FreePostRequestDto.FreePostModify post) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        FreePost freePost = freePostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다."));

        if (user.equals(freePost.getWriter())) {
            String imageList = getJoinByComma(post.getImage());
            String tagList = getJoinByComma(post.getTag());

            freePost.setTitle(post.getTitle());
            freePost.setDetail(post.getDetail());
            freePost.setImage(imageList);
            freePost.setLocation(post.getLocation());
            freePost.setOngoing(post.getOngoing());
            freePost.setTag(tagList);

            freePostRepository.save(freePost);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 수정 접근 권한이 없습니다.");
        }
    }


}
