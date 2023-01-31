package dcom.nearbuybackend.api.domain.user.controller;

import dcom.nearbuybackend.api.domain.user.dto.UserRequestDto;
import dcom.nearbuybackend.api.domain.user.dto.UserResponseDto;
import dcom.nearbuybackend.api.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"User Controller"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("유저 페이지 조회")
    @GetMapping("/page")
    public ResponseEntity<UserResponseDto.UserPageInfo> getUserPage(@RequestParam String id) {
        return ResponseEntity.ok(userService.getUserPage(id));
    }

    @ApiOperation("유저 페이지 수정")
    @PatchMapping("/page")
    public ResponseEntity<Void> modifyUserPage(HttpServletRequest httpServletRequest, @RequestParam String id, @RequestBody UserRequestDto.UserPageModify data) {
        userService.modifyUserPage(httpServletRequest, id, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("유저 비밀번호 변경")
    @PatchMapping("/page/change")
    public ResponseEntity<Void> changeUserPassword(HttpServletRequest httpServletRequest, @RequestBody UserRequestDto.UserChangePassword data) {
        userService.changeUserPassword(httpServletRequest, data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
