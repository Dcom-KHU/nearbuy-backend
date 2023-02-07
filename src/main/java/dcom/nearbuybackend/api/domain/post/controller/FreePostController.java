package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.FreePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.FreePostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.FreePostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Free Post Controller"})
@RequestMapping("/api/post/free")
@RestController
@RequiredArgsConstructor
public class FreePostController {

    private final FreePostService freePostService;

    @ApiOperation("나눔 게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FreePostResponseDto.FreePostInfo> getFreePost(@RequestParam Integer id) {

        return ResponseEntity.ok(freePostService.getFreePost(id));
    }

    @ApiOperation("나눔 게시글 등록")
    @PostMapping
    public ResponseEntity<Void> registerFreePost(HttpServletRequest httpServletRequest,
                                                 @RequestBody FreePostRequestDto.FreePostRegister freePost) {

        freePostService.registerFreePost(httpServletRequest, freePost);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("나눔 게시글 수정")
    @PatchMapping
    public ResponseEntity<Void> modifyFreePost(HttpServletRequest httpServletRequest, @RequestParam Integer id,
                                               @RequestBody FreePostRequestDto.FreePostModify freePost) {

        freePostService.modifyFreePost(httpServletRequest, id, freePost);
        return ResponseEntity.ok().build();
    }
}
