package dcom.nearbuybackend.api.domain.user.controller;

import dcom.nearbuybackend.api.domain.user.dto.UserPageRequestDto;
import dcom.nearbuybackend.api.domain.user.dto.UserPageResponseDto;
import dcom.nearbuybackend.api.domain.user.service.UserPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"User Page Controller"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;

    @ApiOperation("유저 페이지 조회")
    @GetMapping("/page")
    public ResponseEntity<UserPageResponseDto.UserPageInfo> getUserPage(@RequestParam String id) {
        return ResponseEntity.ok(userPageService.getUserPage(id));
    }

    @ApiOperation("유저 페이지 수정")
    @PatchMapping("/page")
    public ResponseEntity<Void> modifyUserPage(HttpServletRequest httpServletRequest, @RequestParam String id, @RequestBody UserPageRequestDto.UserPageModify data) {
        userPageService.modifyUserPage(httpServletRequest, id, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("유저 비밀번호 변경")
    @PatchMapping("/page/change")
    public ResponseEntity<Void> changeUserPassword(HttpServletRequest httpServletRequest, @RequestBody UserPageRequestDto.UserChangePassword data) {
        userPageService.changeUserPassword(httpServletRequest, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("내가 게시한 글 조회")
    @GetMapping("/page/my")
    public ResponseEntity<UserPageResponseDto.MyPostInfo> getMyPost(HttpServletRequest httpServletRequest, @RequestParam String id) {
        return ResponseEntity.ok(userPageService.getMyPost(httpServletRequest, id));
    }

    @ApiOperation("남이 게시한 글 조회")
    @GetMapping("/page/other")
    public ResponseEntity<UserPageResponseDto.OthersPostInfo> getOthersPost(@RequestParam String id) {
        return ResponseEntity.ok(userPageService.getOthersPost(id));
    }

    @ApiOperation("찜한 글 조회")
    @GetMapping("/page/like")
    public ResponseEntity<UserPageResponseDto.LikedPostInfo> getLikedPost(@RequestParam String id) {
        return ResponseEntity.ok(userPageService.getLikedPost(id));
    }

    @ApiOperation("거래 후기 조회")
    @GetMapping("/page/review")
    public ResponseEntity<UserPageResponseDto.UserReviewInfo> getUserReview(@RequestParam String id) {

        return ResponseEntity.ok(userPageService.getUserReview(id));
    }

    @ApiOperation("거래 후기 등록")
    @PostMapping("/page/review")
    public ResponseEntity<Void> registerUserReview(HttpServletRequest httpServletRequest,
                                                   @RequestBody UserPageRequestDto.UserReviewRegister userReview) {

        userPageService.registerUserReview(httpServletRequest, userReview);
        return ResponseEntity.ok().build();
    }
}
