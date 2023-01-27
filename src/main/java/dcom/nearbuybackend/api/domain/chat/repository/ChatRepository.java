package dcom.nearbuybackend.api.domain.chat.repository;

import dcom.nearbuybackend.api.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
}
