package dcom.nearbuybackend.api.domain.user.controller;

import dcom.nearbuybackend.api.domain.user.dto.UserPageRequestDto;
import dcom.nearbuybackend.api.domain.user.dto.UserPageResponseDto;
import dcom.nearbuybackend.api.domain.user.service.UserPageService;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "유저 페이지 - 메인", notes = "입력받은 아이디에 해당하는 유저 페이지를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 유저가 없습니다.")
    })
    @GetMapping("/page")
    public ResponseEntity<UserPageResponseDto.UserPageInfo> getUserPage(@ApiParam(value = "유저 ID", required = true) @RequestParam String id) {
        return ResponseEntity.ok(userPageService.getUserPage(id));
    }

    @ApiOperation(value = "유저 페이지 - 수정", notes = "[인증 필요]입력받은 아이디에 해당하는 유저 페이지를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "유저 페이지 수정 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 유저가 없습니다.")
    })
    @PatchMapping("/page")
    public ResponseEntity<Void> modifyUserPage(HttpServletRequest httpServletRequest, @ApiParam(value = "유저 ID", required = true) @RequestParam String id, @ApiParam(value = "유저 페이지 수정 정보", required = true) @RequestBody UserPageRequestDto.UserPageModify data) {
        userPageService.modifyUserPage(httpServletRequest, id, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "유저 페이지 - 비밀번호 변경", notes = "[인증 필요]입력받은 아이디의 비밀번호를 변경합니다. 일반 로그인만 지원합니다.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "현재 비밀번호가 일치하지 않거나, 새로운 비밀번호와 새로운 비밀번호 확인이 일치하지 않습니다."),
            @ApiResponse(code = 403, message = "소셜 로그인은 비밀번호 변경을 지원하지 않습니다.")
    })
    @PatchMapping("/page/change")
    public ResponseEntity<Void> changeUserPassword(HttpServletRequest httpServletRequest, @ApiParam(value = "유저 비밀번호 변경 정보", required = true) @RequestBody UserPageRequestDto.UserChangePassword data) {
        userPageService.changeUserPassword(httpServletRequest, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "유저 페이지 - 내가 게시한 글(아이디)", notes = "입력받은 아이디에 해당하는 유저의 자신이 게시한 글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 유저가 없습니다.")
    })
    @GetMapping("/page/my/id")
    public ResponseEntity<UserPageResponseDto.MyPostInfo> getMyPostById(@ApiParam(value = "유저 ID", required = true) @RequestParam String id) {
        return ResponseEntity.ok(userPageService.getMyPostById(id));
    }

    @ApiOperation(value = "유저 페이지 - 내가 게시한 글(이름)", notes = "입력받은 이름에 해당하는 유저의 자신이 게시한 글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 유저가 없습니다.")
    })
    @GetMapping("/page/my/name")
    public ResponseEntity<UserPageResponseDto.MyPostInfo> getMyPostByName(@ApiParam(value = "유저 이름", required = true) @RequestParam String name) {
        return ResponseEntity.ok(userPageService.getMyPostByName(name));
    }

    @ApiOperation(value = "유저 페이지 - 남이 게시한 글", notes = "입력받은 아이디에 해당하는 유저이 참여한 남의 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping("/page/other")
    public ResponseEntity<UserPageResponseDto.OthersPostInfo> getOthersPost(@ApiParam(value = "유저 ID", required = true) @RequestParam String id) {
        return ResponseEntity.ok(userPageService.getOthersPost(id));
    }

    @ApiOperation(value = "유저 페이지 - 찜한 글", notes = "입력받은 아이디에 해당하는 유저가 찜한 글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 유저가 없습니다.")
    })
    @GetMapping("/page/like")
    public ResponseEntity<UserPageResponseDto.LikedPostInfo> getLikedPost(@ApiParam(value = "유저 ID", required = true) @RequestParam String id) {
        return ResponseEntity.ok(userPageService.getLikedPost(id));
    }

    @ApiOperation(value = "유저 페이지 - 거래 후기", notes = "입력받은 아이디에 해당하는 유저의 거래 후기를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 유저가 없습니다.")
    })
    @GetMapping("/page/review")
    public ResponseEntity<UserPageResponseDto.UserReviewInfo> getUserReview(@ApiParam(value = "유저 ID", required = true) @RequestParam String id) {

        return ResponseEntity.ok(userPageService.getUserReview(id));
    }

    @ApiOperation(value = "유저 페이지 - 거래 후기 등록", notes = "[인증 필요] 입력받은 아이디에 해당하는 유저의 거래 후기를 등록합니다.")
    @PostMapping("/page/review")
    public ResponseEntity<Void> registerUserReview(HttpServletRequest httpServletRequest, @ApiParam(value = "유저 ID", required = true) @RequestParam String id,
                                                   @ApiParam(value = "유저 거래 후기 정보", required = true) @RequestBody UserPageRequestDto.UserReviewRegister userReview) {

        userPageService.registerUserReview(httpServletRequest, id,  userReview);
        return ResponseEntity.ok().build();
    }
}
