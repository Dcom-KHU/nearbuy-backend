package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.AuctionPost;
import dcom.nearbuybackend.api.domain.post.AuctionPostPeople;
import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.AuctionPostPeopleRepository;
import dcom.nearbuybackend.api.domain.post.repository.AuctionPostRepository;
import dcom.nearbuybackend.api.domain.post.repository.PostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionPostService {
    private final AuctionPostRepository auctionPostRepository;
    private final AuctionPostPeopleRepository auctionPostPeopleRepository;
    private final TokenService tokenService;
    private final PostRepository postRepository;

    // 경매 게시글 조회
    public AuctionPostResponseDto.AuctionPostInfo getAuctionPost(Integer id) {
        AuctionPost auctionPost = auctionPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        return AuctionPostResponseDto.of(auctionPost);
    }

    // 경매 게시글 등록
    public Integer registerAuctionPost(HttpServletRequest httpServletRequest, AuctionPostRequestDto.AuctionPostRegister post) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        String tagString = getJoinByComma(post.getTag());

        AuctionPost auctionPost = new AuctionPost();

        auctionPost.setType("auction");
        auctionPost.setTitle(post.getTitle());
        auctionPost.setWriter(user);
        auctionPost.setDetail(post.getDetail());
        auctionPost.setLocation(post.getLocation());
        auctionPost.setTag(tagString);
        auctionPost.setStartPrice(post.getStartPrice());
        auctionPost.setIncreasePrice(post.getIncreasePrice());
        auctionPost.setDeadline(post.getDeadline());
        auctionPost.setOngoing(true);
        auctionPost.setTime(System.currentTimeMillis());
        auctionPost.setCurrentPrice(post.getStartPrice());

        return auctionPostRepository.save(auctionPost).getId();
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
            String tagString = getJoinByComma(post.getTag());

            auctionPost.setTitle(post.getTitle());
            auctionPost.setWriter(user);
            auctionPost.setDetail(post.getDetail());
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

    // 경매 게시글 참여자 조회
    public AuctionPostResponseDto.AuctionPostPeopleInfo getAuctionPostPeople(HttpServletRequest httpServletRequest, Integer post_id) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(post_id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        if(!user.equals(post.getWriter()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"경매 참여자 조회 접근 권한이 없습니다.");

        List<AuctionPostPeople> auctionPostPeople = auctionPostPeopleRepository.findAllByPostIdOrderByAuctionPriceDesc(post_id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        List<AuctionPostResponseDto.AuctionPeopleInfo> auctionPeopleInfo = new ArrayList<>();
        for (AuctionPostPeople app :
                auctionPostPeople) {
            auctionPeopleInfo.add(AuctionPostResponseDto.AuctionPeopleInfo.of(app));
        }

        return AuctionPostResponseDto.AuctionPostPeopleInfo.of(auctionPeopleInfo);
    }


    // 경매 게시글 참여
    public void joinAuctionPost(HttpServletRequest httpServletRequest, Integer id) {
        AuctionPost auctionPost = auctionPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다"));

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Integer newAuctionPrice = auctionPost.getCurrentPrice() + auctionPost.getIncreasePrice();
        increaseCurrentPrice(id, newAuctionPrice);

        AuctionPostPeople auctionPostPeople = new AuctionPostPeople();
        auctionPostPeople.setId(id);
        auctionPostPeople.setPost(auctionPost);
        auctionPostPeople.setUser(user);
        auctionPostPeople.setAuctionPrice(newAuctionPrice);
        auctionPostPeopleRepository.save(auctionPostPeople);
    }

    private void increaseCurrentPrice(Integer id, Integer newAuctionPrice) {
        AuctionPost auctionPost = auctionPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다"));

        auctionPost.setCurrentPrice(newAuctionPrice);
        auctionPostRepository.save(auctionPost);
    }

}
