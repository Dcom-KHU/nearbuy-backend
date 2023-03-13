package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.ReportPostRequestDto;
import dcom.nearbuybackend.api.domain.post.service.PostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Report Post Controller"})
@RequestMapping("/api/post/report")
@RestController
@RequiredArgsConstructor
public class ReportPostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 신고", notes = "해당하는 게시글을 신고합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PostMapping
    public ResponseEntity<Void> reportPost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id,
                                           @ApiParam(value = "게시글 신고 정보", required = true) @RequestBody ReportPostRequestDto.ReportPost reportPost) {

        postService.reportPost(httpServletRequest, id, reportPost);
        return ResponseEntity.ok().build();
    }
}
