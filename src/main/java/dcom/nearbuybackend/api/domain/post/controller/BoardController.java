package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.BoardResponseDto;
import dcom.nearbuybackend.api.domain.post.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Board Controller"})
@RestController
@RequestMapping("/api/post/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ApiOperation("게시판 조회")
    @GetMapping
    public ResponseEntity<BoardResponseDto.BoardInfo> getBoard(@RequestParam String type, @PageableDefault(size = 9, sort = "time",  direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.getBoard(type, pageable));
    }
}
