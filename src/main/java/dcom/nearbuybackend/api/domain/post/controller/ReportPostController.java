package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.ReportPostRequestDto;
import dcom.nearbuybackend.api.domain.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("게시글 신고")
    @PostMapping
    public ResponseEntity<Void> reportPost(HttpServletRequest httpServletRequest, @RequestParam Integer id,
                                           @RequestBody ReportPostRequestDto.ReportPost reportPost) {

        postService.reportPost(httpServletRequest, id, reportPost);
        return ResponseEntity.ok().build();
    }
}
