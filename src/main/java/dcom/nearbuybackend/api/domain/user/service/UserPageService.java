package dcom.nearbuybackend.api.domain.user.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    private final TokenService tokenService;

    // 유저 페이지 조회
    public UserPageResponseDto.UserPageInfo getUserPage(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
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
    public UserPageResponseDto.MyPostInfo getMyPost(HttpServletRequest httpServletRequest, String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다."));
        User me = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));
        if (!me.equals(user)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "접근할 수 있는 권한이 없습니다.");
        }

        List<UserPageResponseDto.PostInfo> postList = getPostInfoList(me);

        return UserPageResponseDto.MyPostInfo.of(postList);

    }

    private List<UserPageResponseDto.PostInfo> getPostInfoList(User user) {
        List<Optional<Post>> userPost = postRepository.findAllByWriter_Id(user.getId());
        List<UserPageResponseDto.PostInfo> postList = new ArrayList<>();
        for (Optional<Post> optionalPost :
                userPost) {
            Post post = optionalPost.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시글이 없습니다."));
            getPostInfo(postList, post);
        }
        return postList;
    }

    private List<UserPageResponseDto.PostInfo> getLikedPostInfoList(List<UserLike> userLikeList) {
        List<UserPageResponseDto.PostInfo> postList = new ArrayList<>();
        for (UserLike userLike : userLikeList) {
            getPostInfo(postList, userLike.getPost());
        }
        return postList;
    }

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

    public UserPageResponseDto.OthersPostInfo getOthersPost(String id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저가 없습니다"));

        List<UserPageResponseDto.PostInfo> postList = getPostInfoList(user);

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

    public void registerUserReview(HttpServletRequest httpServletRequest,
                                   UserPageRequestDto.UserReviewRegister review) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        UserReview userReview = new UserReview();
        userReview.setUser(user);
        userReview.setEmotion(review.getEmotion());
        userReview.setReply(review.getReply());
        userReview.setLocation(review.getLocation());
        userReview.setTime(review.getTime());
        userReview.setManner(review.getManner());
        userReview.setDetail(review.getDetail());

        userReviewRepository.save(userReview);
    }
}
