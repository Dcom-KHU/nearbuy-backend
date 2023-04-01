package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.FreePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.FreePostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.FreePostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"Free Post Controller"})
@RequestMapping("/api/post/free")
@RestController
@RequiredArgsConstructor
public class FreePostController {

    private final FreePostService freePostService;

    @ApiOperation(value = "나눔 게시글 조회", notes = "나눔 게시글 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FreePostResponseDto.FreePostInfo> getFreePost(@ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {

        return ResponseEntity.ok(freePostService.getFreePost(id));
    }

    @ApiOperation(value = "나눔 게시글 등록", notes = "[인증 필요] 나눔 게시글 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Integer>> registerFreePost(HttpServletRequest httpServletRequest,
                                                                 @ApiParam(value = "나눔 게시글 등록 정보", required = true) @RequestBody FreePostRequestDto.FreePostRegister freePost) {

        Integer id = freePostService.registerFreePost(httpServletRequest, freePost);
        Map<String, Integer> data = new HashMap<>();
        data.put("postId", id);
        return ResponseEntity.ok().body(data);
    }

    @ApiOperation(value = "나눔 게시글 수정", notes = "[인증 필요] 나눔 게시글 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 수정 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Void> modifyFreePost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id,
                                               @ApiParam(value = "나눔 게시글 수정 정보", required = true) @RequestBody FreePostRequestDto.FreePostModify freePost) {

        freePostService.modifyFreePost(httpServletRequest, id, freePost);
        return ResponseEntity.ok().build();
    }
}
