package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.GroupPostPeopleResponseDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.GroupPostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Group Post Controller"})
@RequestMapping("/api/post/group")
@RestController
@RequiredArgsConstructor
public class GroupPostController {

    private final GroupPostService groupPostService;

    @ApiOperation(value = "공구 게시글 조회", notes = "공구 게시글 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GroupPostResponseDto.GroupPostInfo> getGroupPost(@ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {

        return ResponseEntity.ok(groupPostService.getGroupPost(id));
    }

    @ApiOperation(value = "공구 게시글 등록", notes = "[인증 필요] 공구 게시글 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> registerGroupPost(HttpServletRequest httpServletRequest,
                                                  @ApiParam(value = "공구 게시글 등록 정보", required = true) @RequestBody GroupPostRequestDto.GroupPostRegister groupPost) {

        groupPostService.registerGroupPost(httpServletRequest, groupPost);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "공구 게시글 수정", notes = "[인증 필요] 공구 게시글 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 수정 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Void> modifyGroupPost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id,
                                                @ApiParam(value = "공구 게시글 수정 정보", required = true) @RequestBody GroupPostRequestDto.GroupPostModify groupPost) {

        groupPostService.modifyGroupPost(httpServletRequest, id, groupPost);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "공구 게시글 참여", notes = "[인증 필요] 공구 게시글에 참여합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PostMapping("/participate")
    public ResponseEntity<Void> participateGroupPost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {

        groupPostService.participateGroupPost(httpServletRequest, id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "공구 게시글 참여자 조회", notes = "[인증 필요] 공구 게시글 참여자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "공구 게시글 참여자 조회 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping("/participate")
    public ResponseEntity<GroupPostPeopleResponseDto.GroupPostPeopleInfo> GetGroupPostPeople(
            HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {

        return ResponseEntity.ok(groupPostService.getGroupPostPeople(httpServletRequest, id));
    }

    @ApiOperation(value = "공구 게시글 참여자 거절", notes = "[인증 필요] 공구 게시글 참여자를 리스트에서 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "공구 게시글 참여자 조회 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없거나, 사용자의 name이 존재하지 않거나, 해당 사용자가 공구에 참여중이지 않습니다.")
    })
    @PatchMapping("/participate")
    public ResponseEntity<Void> refuseGroupPostPeople(
            HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id, @ApiParam(value = "사용자 이름(닉네임)", required = true) @RequestParam String name) {

        groupPostService.refuseGroupPostPeople(httpServletRequest, id, name);
        return ResponseEntity.ok().build();
    }
}
