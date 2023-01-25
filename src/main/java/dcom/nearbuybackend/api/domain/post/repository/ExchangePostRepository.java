package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.ExchangePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangePostRepository extends JpaRepository<ExchangePost, Integer> {
}
