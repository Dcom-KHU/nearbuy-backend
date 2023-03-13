package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.SalePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.SalePostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.SalePostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Sale Post Controller"})
@RestController
@RequestMapping("/api/post/sale")
@RequiredArgsConstructor
public class SalePostController {
    private final SalePostService salePostService;

    @ApiOperation(value = "판매 게시글 조회", notes = "판매 게시글 정보를 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SalePostResponseDto.SalePostInfo> getSalePost(@ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        return ResponseEntity.ok(salePostService.getSalePost(id));
    }

    @ApiOperation(value = "판매 게시글 등록", notes = "[인증 필요]판매 게시글을 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> registerSalePost(HttpServletRequest httpServletRequest, @ApiParam(value = "판매 게시글 등록 정보", required = true) @RequestBody SalePostRequestDto.SalePostRegister post) {
        salePostService.registerSalePost(httpServletRequest, post);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "판매 게시글 수정", notes = "[인증 필요]판매 게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 수정 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Void> modifySalePost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id, @ApiParam(value = "판매 게시글 수정 정보", required = true) @RequestBody SalePostRequestDto.SalePostModify post) {
        salePostService.modifySalePost(httpServletRequest, id, post);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
