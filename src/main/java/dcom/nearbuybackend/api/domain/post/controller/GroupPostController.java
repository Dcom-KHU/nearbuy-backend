package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.GroupPostPeopleResponseDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.GroupPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("공구 게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GroupPostResponseDto.GroupPostInfo> getGroupPost(@RequestParam Integer id) {

        return ResponseEntity.ok(groupPostService.getGroupPost(id));
    }

    @ApiOperation("공구 게시글 등록")
    @PostMapping
    public ResponseEntity<Void> registerGroupPost(HttpServletRequest httpServletRequest,
                                                  @RequestBody GroupPostRequestDto.GroupPostRegister groupPost) {

        groupPostService.registerGroupPost(httpServletRequest, groupPost);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("공구 게시글 수정")
    @PatchMapping
    public ResponseEntity<Void> modifyGroupPost(HttpServletRequest httpServletRequest, @RequestParam Integer id,
                                                @RequestBody GroupPostRequestDto.GroupPostModify groupPost) {

        groupPostService.modifyGroupPost(httpServletRequest, id, groupPost);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("공구 게시글 참여")
    @PostMapping("/participate")
    public ResponseEntity<Void> participateGroupPost(HttpServletRequest httpServletRequest, @RequestParam Integer id) {

        groupPostService.participateGroupPost(httpServletRequest, id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("공구 게시글 참여자 조회")
    @GetMapping("/participate")
    public ResponseEntity<GroupPostPeopleResponseDto.GroupPostPeopleInfo> GetGroupPostPeople(
            HttpServletRequest httpServletRequest, @RequestParam Integer id) {

        return ResponseEntity.ok(groupPostService.getGroupPostPeople(httpServletRequest, id));
    }

    @ApiOperation("공구 게시글 참여자 거절")
    @PatchMapping("/participate")
    public ResponseEntity<Void> refuseGroupPostPeople(
            HttpServletRequest httpServletRequest, @RequestParam Integer id, @RequestParam String name) {

        groupPostService.refuseGroupPostPeople(httpServletRequest, id, name);
        return ResponseEntity.ok().build();
    }
}
