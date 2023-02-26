package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.ExchangePost;
import dcom.nearbuybackend.api.domain.post.dto.ExchangePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.ExchangePostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.ExchangePostRepository;
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
public class ExchangePostService {
    private final ExchangePostRepository exchangePostRepository;

    private final TokenService tokenService;

    // 교환 게시글 조회
    public ExchangePostResponseDto.ExchangePostInfo getExchangePost(Integer id) {
        ExchangePost exchangePost = exchangePostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다.")
        );

        return ExchangePostResponseDto.ExchangePostInfo.of(exchangePost);
    }

    // 교환 게시글 등록
    public void registerExchangePost(HttpServletRequest httpServletRequest, ExchangePostRequestDto.ExchangePostRegister post){
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        String imageString = getJoinByComma(post.getImage());
        String tagString = getJoinByComma(post.getTag());

        ExchangePost exchangePost = new ExchangePost();
        exchangePost.setType("exchange");
        exchangePost.setTitle(post.getTitle());
        exchangePost.setWriter(user);
        exchangePost.setDetail(post.getDetail());
        exchangePost.setImage(imageString);
        exchangePost.setTime(System.currentTimeMillis());
        exchangePost.setLocation(post.getLocation());
        exchangePost.setOngoing(true);
        exchangePost.setTag(tagString);
        exchangePost.setTarget(post.getTarget());

        exchangePostRepository.save(exchangePost);

    }

    private static String getJoinByComma(List<String> list) {
        return String.join(",", list);
    }

    // 교환 게시글 수정
    public void modifyExchangePost(HttpServletRequest httpServletRequest, Integer id, ExchangePostRequestDto.ExchangePostModify post) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        ExchangePost newExchangePost = exchangePostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));
        if (user.equals(newExchangePost.getWriter())) {
            String imageList = getJoinByComma(post.getImage());
            String tagList = getJoinByComma(post.getTag());

            newExchangePost.setTitle(post.getTitle());
            newExchangePost.setDetail(post.getDetail());
            newExchangePost.setImage(imageList);
            newExchangePost.setLocation(post.getLocation());
            newExchangePost.setOngoing(post.getOngoing());
            newExchangePost.setTag(tagList);
            newExchangePost.setTarget(post.getTarget());

            exchangePostRepository.save(newExchangePost);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 수정 접근 권한이 없습니다.");
        }
    }
}
