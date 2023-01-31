package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.repository.PostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final TokenService tokenService;

    // 게시글 삭제
    public void deletePost(HttpServletRequest httpServletRequest, Integer id) {
        User user =  tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 게시물이 없습니다."));

        if(user.equals(post.getWriter())) {
            postRepository.deleteById(id);
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"게시물 삭제 접근 권한이 없습니다.");
    }
}
