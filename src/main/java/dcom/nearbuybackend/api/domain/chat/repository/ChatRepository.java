package dcom.nearbuybackend.api.domain.chat.repository;

import dcom.nearbuybackend.api.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {

    // 유저가 속해있는 채팅방 별 채팅 목록 확인
    @Query("{room: ?0, userList: {$in: [?1]}}")
    List<Chat> findAllByRoomAndUser(Integer room, String user);

    // 유저가 속해있는 채팅방 각각의 마지막 채팅 목록 조회
    @Query("{userList: {$in: [?0]}, last: true}")
    List<Chat> findAllByUser(String user);

    Chat findFirstByRoomOrderByTimeDesc(Integer room);
}
