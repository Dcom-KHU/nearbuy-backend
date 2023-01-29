package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.SalePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.SalePostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.SalePostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Sale Post Controller"})
@RestController
@RequestMapping("/api/post/sale")
@RequiredArgsConstructor
public class SalePostController {
    private final SalePostService salePostService;

    @ApiOperation("판매 게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SalePostResponseDto.SalePostInfo> getSalePost(@RequestParam Integer id) {
        return ResponseEntity.ok(salePostService.getSalePost(id));
    }

    @ApiOperation("판매 게시글 등록")
    @PostMapping
    public ResponseEntity<Void> registerSalePost(@RequestBody SalePostRequestDto.SalePostRegister post) {
        salePostService.registerSalePost(post);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("판매 게시글 수정")
    @PatchMapping
    public ResponseEntity<Void> modifySalePost(@RequestParam Integer id, @RequestBody SalePostRequestDto.SalePostModify post) {
        salePostService.modifySalePost(id, post);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
