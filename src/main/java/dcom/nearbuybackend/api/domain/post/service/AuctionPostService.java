package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.AuctionPost;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.AuctionPostRepository;
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
public class AuctionPostService {
    private final AuctionPostRepository auctionPostRepository;
    private final TokenService tokenService;

    // 경매 게시글 조회
    public AuctionPostResponseDto.AuctionPostInfo getAuctionPost(Integer id) {
        AuctionPost auctionPost = auctionPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        return AuctionPostResponseDto.of(auctionPost);
    }

    // 경매 게시글 등록
    public void registerAuctionPost(HttpServletRequest httpServletRequest, AuctionPostRequestDto.AuctionPostRegister post) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        String imageString = getJoinByComma(post.getImage());
        String tagString = getJoinByComma(post.getTag());

        AuctionPost auctionPost = new AuctionPost();

        auctionPost.setTitle(post.getTitle());
        auctionPost.setWriter(user);
        auctionPost.setDetail(post.getDetail());
        auctionPost.setImage(imageString);
        auctionPost.setLocation(post.getLocation());
        auctionPost.setTag(tagString);
        auctionPost.setStartPrice(post.getStartPrice());
        auctionPost.setIncreasePrice(post.getIncreasePrice());
        auctionPost.setDeadline(post.getDeadline());
        auctionPost.setOngoing(true);
        auctionPost.setTime(System.currentTimeMillis());
        auctionPost.setCurrentPrice(post.getStartPrice());

        auctionPostRepository.save(auctionPost);
    }

    private static String getJoinByComma(List<String> list) {
        return String.join(",", list);
    }

    // 경매 게시글 수정
    public void modifyAuctionPost(HttpServletRequest httpServletRequest, Integer id, AuctionPostRequestDto.AuctionPostModify post) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        AuctionPost auctionPost = auctionPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        if (user.equals(auctionPost.getWriter())) {
            String imageString = getJoinByComma(post.getImage());
            String tagString = getJoinByComma(post.getTag());

            auctionPost.setTitle(post.getTitle());
            auctionPost.setWriter(user);
            auctionPost.setDetail(post.getDetail());
            auctionPost.setImage(imageString);
            auctionPost.setLocation(post.getLocation());
            auctionPost.setOngoing(post.getOngoing());
            auctionPost.setTag(tagString);
            auctionPost.setIncreasePrice(post.getIncreasePrice());
            auctionPost.setDeadline(post.getDeadline());

            auctionPostRepository.save(auctionPost);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 수정 접근 권한이 없습니다.");
        }

    }
}
