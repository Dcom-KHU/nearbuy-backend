package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.ReportPost;
import dcom.nearbuybackend.api.domain.post.dto.ReportPostRequestDto;
import dcom.nearbuybackend.api.domain.post.repository.PostRepository;
import dcom.nearbuybackend.api.domain.post.repository.ReportPostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.UserLike;
import dcom.nearbuybackend.api.domain.user.repository.UserLikeRepository;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ReportPostRepository reportPostRepository;

    private final TokenService tokenService;
    private final UserLikeRepository userLikeRepository;

    // 게시글 삭제
    public void deletePost(HttpServletRequest httpServletRequest, Integer id) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        if (user.equals(post.getWriter())) {
            postRepository.deleteById(id);
        } else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 삭제 접근 권한이 없습니다.");
    }

    // 게시글 신고
    public void reportPost(HttpServletRequest httpServletRequest, Integer id, ReportPostRequestDto.ReportPost report) {

        tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        ReportPost reportPost = new ReportPost();
        reportPost.setPost(post);
        reportPost.setType(report.getType());
        reportPost.setDetail(report.getDetail());

        reportPostRepository.save(reportPost);
    }

    // 게시글 찜 여부 조회
    public boolean getIsLiked(HttpServletRequest httpServletRequest, Integer id) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        List<UserLike> userLikeList = userLikeRepository.findAllByUser(user);
        for (UserLike userLike :
                userLikeList) {
            if (userLike.getPost().getId().equals(post.getId())) {
                return true;
            }
        }
        return false;
    }


}
