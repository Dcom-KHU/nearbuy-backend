package dcom.nearbuybackend.api.domain.user.service;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.repository.ChatRepository;
import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.repository.*;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.UserLike;
import dcom.nearbuybackend.api.domain.user.UserReview;
import dcom.nearbuybackend.api.domain.user.dto.UserPageRequestDto;
import dcom.nearbuybackend.api.domain.user.dto.UserPageResponseDto;
import dcom.nearbuybackend.api.domain.user.repository.UserLikeRepository;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.domain.user.repository.UserReviewRepository;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPageService {

    private final UserRepository userRepository;
    private final UserLikeRepository userLikeRepository;
    private final UserReviewRepository userReviewRepository;
    private final PostRepository postRepository;
    private final SalePostRepository salePostRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final FreePostRepository freePostRepository;
    private final AuctionPostRepository auctionPostRepository;
    private final GroupPostRepository groupPostRepository;
    private final GroupPostPeopleRepository groupPostPeopleRepository;
    private final ChatRepository chatRepository;

    private final TokenService tokenService;

    // 유저 페이지 조회
    public UserPageResponseDto.UserPageInfo getUserPageById(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."));

        return UserPageResponseDto.UserPageInfo.of(user);
    }

    public UserPageResponseDto.UserPageInfo getUserPageByName(String name) {
        User user = userRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."));

        return UserPageResponseDto.UserPageInfo.of(user);
    }

    // 유저 페이지 수정
    public void modifyUserPage(HttpServletRequest httpServletRequest, String id, UserPageRequestDto.UserPageModify data) {
        User user1 = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        User user2 = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."));

        if (user1.equals(user2)) {
            user2.setName(data.getName());
            user2.setImage(data.getImage());
            user2.setLocation(data.getLocation());

            userRepository.save(user2);
        } else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유저 페이지 수정 접근 권한이 없습니다.");
    }

    // 유저 비밀번호 변경
    public void changeUserPassword(HttpServletRequest httpServletRequest, UserPageRequestDto.UserChangePassword data) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        if (user.getSocial().equals(false)) {
            if (user.getPassword().equals(data.getPassword())) {
                if (data.getNewPassword().equals(data.getNewPasswordCheck())) {
                    user.setPassword(data.getNewPassword());

                    userRepository.save(user);
                } else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새로운 비밀번호와 새로운 비밀번호 확인이 일치하지 않습니다.");
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다.");
        } else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "소셜 로그인은 비밀번호 변경을 지원하지 않습니다.");
    }

    // 내가 게시한 글 조회
    public UserPageResponseDto.MyPostInfo getMyPostById(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."));

        List<UserPageResponseDto.PostInfo> postList = getPostInfoList(user);

        return UserPageResponseDto.MyPostInfo.of(postList);
    }

    public UserPageResponseDto.MyPostInfo getMyPostByName(String name) {
        User user = userRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."));

        List<UserPageResponseDto.PostInfo> postList = getPostInfoList(user);

        return UserPageResponseDto.MyPostInfo.of(postList);
    }

    // 내 게시글 리스트 조회
    private List<UserPageResponseDto.PostInfo> getPostInfoList(User user) {
        List<Optional<Post>> userPost = postRepository.findAllByWriter(user);
        List<UserPageResponseDto.PostInfo> postList = new ArrayList<>();
        for (Optional<Post> optionalPost :
                userPost) {
            Post post = optionalPost.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시글이 없습니다."));
            getPostInfo(postList, post);
        }
        return postList;
    }

    // 찜 게시글 리스트 조회
    private List<UserPageResponseDto.PostInfo> getLikedPostInfoList(List<UserLike> userLikeList) {
        List<UserPageResponseDto.PostInfo> postList = new ArrayList<>();
        for (UserLike userLike : userLikeList) {
            getPostInfo(postList, userLike.getPost());
        }
        return postList;
    }

    // 게시글 조회
    private void getPostInfo(List<UserPageResponseDto.PostInfo> postList, Post post) {
        String type = post.getType();

        if (type.equals("sale")) {
            postList.add(UserPageResponseDto.PostInfo.ofSale(salePostRepository.findById(post.getId()).get()));
        } else if (type.equals("exchange")) {
            postList.add(UserPageResponseDto.PostInfo.ofExchange(exchangePostRepository.findById(post.getId()).get()));
        } else if (type.equals("free")) {
            postList.add(UserPageResponseDto.PostInfo.ofFree(freePostRepository.findById(post.getId()).get()));
        } else if (type.equals("auction")) {
            postList.add(UserPageResponseDto.PostInfo.ofAuction(auctionPostRepository.findById(post.getId()).get()));
        } else if (type.equals("group")) {
            List<GroupPostPeople> participants = groupPostPeopleRepository.findByPost(post);
            Integer currentPeople = getCurrentPeople(participants);
            postList.add(UserPageResponseDto.PostInfo.ofGroup(groupPostRepository.findById(post.getId()).get(), currentPeople));
        }
    }

    // 타인 게시글 조회
    public UserPageResponseDto.OthersPostInfo getOthersPost(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다"));

        List<Chat> chatList = chatRepository.findAllByUser(id);

        List<UserPageResponseDto.PostInfo> postList = new ArrayList<>();

        for(Chat c : chatList) {
            Post post = postRepository.findById(c.getPost()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시글이 없습니다"));

            getPostInfo(postList, post);
        }

        return UserPageResponseDto.OthersPostInfo.of(postList);
    }


    private static Integer getCurrentPeople(List<GroupPostPeople> participants) {
        Integer currentPeople = 0;
        for (GroupPostPeople part :
                participants) {
                currentPeople++;
        }
        return currentPeople;
    }

    public UserPageResponseDto.LikedPostInfo getLikedPost(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다"));

        List<UserPageResponseDto.PostInfo> postList = getLikedPostInfoList(userLikeRepository.findAllByUser(user));

        return UserPageResponseDto.LikedPostInfo.of(postList);
    }

    public UserPageResponseDto.UserReviewInfo getUserReview(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다"));

        List<UserReview> reviewList = userReviewRepository.findAllByUser(user);
        List<UserPageResponseDto.ReviewInfo> reviewInfoList = new ArrayList<>();
        for (UserReview userReview: reviewList) {
            reviewInfoList.add(UserPageResponseDto.ReviewInfo.of(userReview));
        }

        return UserPageResponseDto.UserReviewInfo.of(user, reviewInfoList);
    }

    public void registerUserReview(HttpServletRequest httpServletRequest, String id,
                                   UserPageRequestDto.UserReviewRegister review) {

        User reviewer = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        User reviewee = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 유저가 존재하지 않습니다."));

        UserReview userReview = new UserReview();

        userReview.setUser(reviewee);

        if(!review.getEmotion().equals("GOOD") && !review.getEmotion().equals("SOSO") && !review.getEmotion().equals("BAD"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "감정(GOOD, SOSO, BAD) 입력을 잘못 하셨습니다.");
        else if(review.getEmotion().equals("GOOD")) {
            reviewee.setMannerPoint(reviewee.getMannerPoint() + 0.5);
        }
        else if(review.getEmotion().equals("BAD")) {
            reviewee.setMannerPoint(reviewee.getMannerPoint() - 0.5);
        }

        userReview.setEmotion(review.getEmotion());
        userReview.setReply(review.getReply());
        userReview.setLocation(review.getLocation());
        userReview.setTime(review.getTime());
        userReview.setManner(review.getManner());
        userReview.setDetail(review.getDetail());

        Double totalScore = giveScoreUserReview(review.getReply())
                + giveScoreUserReview(review.getTime())
                + giveScoreUserReview(review.getLocation())
                + giveScoreUserReview(review.getManner());

        reviewee.setMannerPoint(reviewee.getMannerPoint() + totalScore);

        userRepository.save(reviewee);
        userReviewRepository.save(userReview);
    }

    public Double giveScoreUserReview(Boolean item){
        if(item)
            return 0.1;
        else
            return -0.1;
    }
}
