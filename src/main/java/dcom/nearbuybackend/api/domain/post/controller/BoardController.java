package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.BoardResponseDto;
import dcom.nearbuybackend.api.domain.post.service.BoardService;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "게시판 조회", notes = "타입별로 해당하는 게시글 리스트를 조회합니다.")
    @ApiResponses ({
            @ApiResponse(code = 400, message = "게시글 타입을 잘못 입력하셨습니다.")
    })
    @GetMapping
    public ResponseEntity<BoardResponseDto.BoardInfo> getBoard(@ApiParam(value = "게시글 타입(all, sale, exchange, free, auction, group)", required = true) @RequestParam String type, @PageableDefault(size = 9, sort = "time",  direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.getBoard(type, pageable));
    }

    @ApiOperation(value = "게시판 검색", notes = "타입별로 검색 내용이 포함되는 해당하는 게시글 리스트를 조회합니다.")
    @ApiResponses ({
            @ApiResponse(code = 400, message = "게시글 타입을 잘못 입력하셨습니다.")
    })
    @GetMapping("/search")
    public ResponseEntity<BoardResponseDto.BoardInfo> searchBoard(@ApiParam(value = "게시글 타입(all, sale, exchange, free, auction, group)", required = true) @RequestParam String type, @RequestParam String search, @PageableDefault(size = 9, sort = "time",  direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.searchBoard(type, search, pageable));
    }
}
