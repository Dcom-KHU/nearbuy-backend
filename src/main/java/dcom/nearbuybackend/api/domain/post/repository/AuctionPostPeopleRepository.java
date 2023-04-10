package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.AuctionPostPeople;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionPostPeopleRepository extends JpaRepository<AuctionPostPeople, Integer> {
     Optional<List<AuctionPostPeople>> findAllByPostIdOrderByAuctionPriceDesc(Integer post_id);

     AuctionPostPeople findByPostIdAndUserId(Integer postId, String userId);
}
