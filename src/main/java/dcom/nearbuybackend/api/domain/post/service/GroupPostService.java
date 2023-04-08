package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.repository.ChatRepository;
import dcom.nearbuybackend.api.domain.post.AuctionPost;
import dcom.nearbuybackend.api.domain.post.GroupPost;
import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostPeopleResponseDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.GroupPostPeopleRepository;
import dcom.nearbuybackend.api.domain.post.repository.GroupPostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static dcom.nearbuybackend.api.domain.chat.service.ChatService.room;

@Service
@RequiredArgsConstructor
public class GroupPostService {

    private final GroupPostRepository groupPostRepository;
    private final TokenService tokenService;
    private final GroupPostPeopleRepository groupPostPeopleRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    /**
     * 공구 게시글 조회
     */
    public GroupPostResponseDto.GroupPostInfo getGroupPost(Integer id) {

        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        return GroupPostResponseDto.GroupPostInfo.of(groupPost);
    }

    /**
     * 공구 게시글 등록
     */
    public Integer registerGroupPost(HttpServletRequest httpServletRequest, GroupPostRequestDto.GroupPostRegister post) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = new GroupPost();

        groupPost.setType("group");
        groupPost.setTitle(post.getTitle());
        groupPost.setWriter(user);
        groupPost.setDetail(post.getDetail());
        groupPost.setTime(System.currentTimeMillis());
        groupPost.setLocation(post.getLocation());
        groupPost.setOngoing(true);
        groupPost.setTag(getJoinByComma(post.getTag()));
        groupPost.setGroupPrice(post.getGroupPrice());
        groupPost.setTotalPeople(post.getTotalPeople());
        groupPost.setCurrentPeople(1);
        groupPost.setDistribute(post.getDistribute());
        groupPost.setDay(post.getDay().stream().map(l -> Long.toString(l)).collect(Collectors.joining(",")));

        return groupPostRepository.save(groupPost).getId();
    }

    private static String getJoinByComma(List<String> list) {
        return String.join(",", list);
    }

    /**
     * 공구 게시글 수정
     */
    public void modifyGroupPost(HttpServletRequest httpServletRequest, Integer id,
                                GroupPostRequestDto.GroupPostModify post) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        if (user.equals(groupPost.getWriter())) {
            String tagList = getJoinByComma(post.getTag());

            groupPost.setTitle(post.getTitle());
            groupPost.setDetail(post.getDetail());
            groupPost.setLocation(post.getLocation());
            groupPost.setOngoing(post.getOngoing());
            groupPost.setTag(tagList);
            groupPost.setGroupPrice(post.getGroupPrice());
            groupPost.setTotalPeople(post.getTotalPeople());

            groupPostRepository.save(groupPost);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 수정 접근 권한이 없습니다.");
        }
    }

    /**
     * 공구 게시글 참여
     */
    public void participateGroupPost(HttpServletRequest httpServletRequest, Integer id) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        groupPostPeopleRepository.findByPostAndUser(groupPost, user).ifPresent(groupPostPeople -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "해당 게시글에 이미 참여한 user 입니다.");
        });


        if (Objects.equals(groupPost.getTotalPeople(), groupPost.getCurrentPeople()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"공구 인원이 꽉 찼습니다.");
        else
            groupPost.setCurrentPeople(groupPost.getCurrentPeople() + 1);

        GroupPostPeople groupPostPeople = new GroupPostPeople();
        groupPostPeople.setPost(groupPost);
        groupPostPeople.setUser(user);
        groupPostPeople.setParticipate(true);

        groupPostRepository.save(groupPost);
        groupPostPeopleRepository.save(groupPostPeople);
    }

    /**
     * 공구 게시글 참여자 조회
     */
    public GroupPostPeopleResponseDto.GroupPostPeopleInfo getGroupPostPeople(
            HttpServletRequest httpServletRequest, Integer id) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        if (!user.equals(groupPost.getWriter())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "참여자 조회 접근 권한이 없습니다.");
        }

        List<GroupPostPeopleResponseDto.GroupPostPersonInfo> groupPostPersonInfos = new ArrayList<>();

        List<GroupPostPeople> groupPostPeopleList = groupPostPeopleRepository.findByPost(groupPost);

        for (GroupPostPeople groupPostPeople : groupPostPeopleList) {
            groupPostPersonInfos.add(GroupPostPeopleResponseDto.GroupPostPersonInfo.of(groupPostPeople));
        }

        return GroupPostPeopleResponseDto.GroupPostPeopleInfo.of(groupPostPersonInfos);
    }

    /**
     * 공구 게시글 참여자 거절
     */
    public void refuseGroupPostPeople(HttpServletRequest httpServletRequest, Integer id, String name) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        if (!user.equals(groupPost.getWriter())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "참여자 거절 접근 권한이 없습니다.");
        }

        User target = userRepository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 name의 사용자를 찾을 수 없습니다.")
        );

        GroupPostPeople groupPostPeople = groupPostPeopleRepository.findByPostAndUser(groupPost, target).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자는 참여하고 있지 않습니다.")
        );

        groupPostPeople.setParticipate(false);

        groupPostPeopleRepository.save(groupPostPeople);
    }

    public void finishGroupPost(HttpServletRequest httpServletRequest, Integer id) {
        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다"));

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        if(!user.equals(groupPost.getWriter()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"경매 참여자 낙찰 접근 권한이 없습니다.");

        List<String> userIdList = new ArrayList<>();
        userIdList.add(user.getId());

        List<String> userNameList = new ArrayList<>();
        userNameList.add(user.getName());

        List<GroupPostPeople> groupPostPeopleList = groupPostPeopleRepository.findByPost(groupPost);

        for(GroupPostPeople g : groupPostPeopleList) {
            if(g.getParticipate().equals(true)) {
                userIdList.add(g.getUser().getId());
                userNameList.add(g.getUser().getName());
            }
        }

        Chat chat = Chat.builder()
                .room(room++)
                .userNameList(userIdList)
                .userNameList(userNameList)
                .message("[SYSTEM]" + userNameList.toString() + " 님이 입장하셨습니다.")
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        chatRepository.save(chat);
    }
}
