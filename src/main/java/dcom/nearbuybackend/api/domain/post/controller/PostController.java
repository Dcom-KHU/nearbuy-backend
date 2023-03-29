package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.service.PostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Post Controller"})
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 삭제", notes = "[인증 필요] 입력받은 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 삭제 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @DeleteMapping
    public ResponseEntity<Void> deletePost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        postService.deletePost(httpServletRequest, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "게시글 찜 여부 조회")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 삭제 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping("/like")
    public ResponseEntity<Boolean> getIsLiked(HttpServletRequest httpServletRequest, @RequestParam Integer id) {
        return ResponseEntity.ok(postService.getIsLiked(httpServletRequest, id));
    }

    @ApiOperation(value = "게시글 찜 등록 / 등록 해제")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 삭제 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PatchMapping("like")
    public ResponseEntity<Void> likePost(HttpServletRequest httpServletRequest, @RequestParam Integer id) {
        postService.likePost(httpServletRequest, id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글 작성자 검증", notes = "[인증 필요] 해당하는 게시글 작성자인지 검증합니다.")
    @GetMapping("/validate")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    public ResponseEntity<Boolean> validateWriter(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        return ResponseEntity.ok(postService.validateWriter(httpServletRequest, id));
    }
}
