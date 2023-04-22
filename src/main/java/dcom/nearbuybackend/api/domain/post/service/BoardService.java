package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.dto.BoardResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final SalePostRepository salePostRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final FreePostRepository freePostRepository;
    private final AuctionPostRepository auctionPostRepository;
    private final GroupPostRepository groupPostRepository;


    // 게시판 조회
    public BoardResponseDto.BoardInfo getBoard(String type, Pageable pageable) {
        List<BoardResponseDto.BoardPostInfo> boardPostInfos = new ArrayList<>();

        Integer total;

        if(type.equals("all"))
            total = boardRepository.findAllPost().size();
        else
            total = boardRepository.findAllPostByType(type).size();

        if(type.equals("all")){
            Page<Post> posts = boardRepository.findAll(pageable);

            for(Post p : posts) {
                if(p.getType().equals("sale")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofSale(salePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("exchange")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofExchange(exchangePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("free")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofFree(freePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("auction")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofAuction(auctionPostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("group")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofGroup(groupPostRepository.findById(p.getId()).get()));
                }
            }
        }
        else if(type.equals("sale") || type.equals("exchange") || type.equals("free") || type.equals("auction") || type.equals("group")) {
            List<Post> posts = boardRepository.findAllByType(type, pageable);

            for(Post p : posts) {
                if(p.getType().equals("sale")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofSale(salePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("exchange")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofExchange(exchangePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("free")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofFree(freePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("auction")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofAuction(auctionPostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("group")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofGroup(groupPostRepository.findById(p.getId()).get()));
                }
            }
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"게시글 type을 잘못 입력하셨습니다.");

        return BoardResponseDto.BoardInfo.of(pageable, boardPostInfos, total);
    }

    // 게시판 검색
    public BoardResponseDto.BoardInfo searchBoard(String type, String search, Pageable pageable) {
        List<BoardResponseDto.BoardPostInfo> boardPostInfos = new ArrayList<>();

        Integer total;

        if(type.equals("all"))
            total = boardRepository.findAllPost().size();
        else
            total = boardRepository.findAllPostByType(type).size();

        if(type.equals("all")){
            List<Post> posts = boardRepository.findAllBySearch(search, pageable);

            for(Post p : posts) {
                if(p.getType().equals("sale")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofSale(salePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("exchange")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofExchange(exchangePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("free")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofFree(freePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("auction")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofAuction(auctionPostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("group")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofGroup(groupPostRepository.findById(p.getId()).get()));
                }
            }
        }
        else if(type.equals("sale") || type.equals("exchange") || type.equals("free") || type.equals("auction") || type.equals("group")) {
            List<Post> posts = boardRepository.findAllByTypeAndSearch(type, search, pageable);

            for(Post p : posts) {
                if(p.getType().equals("sale")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofSale(salePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("exchange")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofExchange(exchangePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("free")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofFree(freePostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("auction")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofAuction(auctionPostRepository.findById(p.getId()).get()));
                }
                else if(p.getType().equals("group")) {
                    boardPostInfos.add(BoardResponseDto.BoardPostInfo.ofGroup(groupPostRepository.findById(p.getId()).get()));
                }
            }
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"게시글 type을 잘못 입력하셨습니다.");

        return BoardResponseDto.BoardInfo.of(pageable, boardPostInfos, total);
    }
}
