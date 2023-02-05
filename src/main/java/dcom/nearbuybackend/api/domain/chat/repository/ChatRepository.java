package dcom.nearbuybackend.api.domain.chat.repository;

import dcom.nearbuybackend.api.domain.chat.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    // 유저가 속해있는 채팅방 별 채팅 목록 확인
    @Tailable
    @Query("{room: ?0, users: {$regex: ?1}}")
    Flux<Chat> findAllByRoomAndUsers(Integer room, String userId);

    // 유저가 속해있는 채팅방 각각의 마지막 채팅 목록 조회
    @Tailable
    @Query("{users: {$regex: ?0}, last: true}")
    Flux<Chat> findAllByUsers(String userId);
}
