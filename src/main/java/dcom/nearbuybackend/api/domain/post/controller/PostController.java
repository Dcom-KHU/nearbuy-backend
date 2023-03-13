package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.service.PostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
