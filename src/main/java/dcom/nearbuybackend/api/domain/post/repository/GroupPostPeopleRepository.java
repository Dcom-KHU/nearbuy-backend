package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupPostPeopleRepository extends JpaRepository<GroupPostPeople, Integer> {
    List<Optional<GroupPostPeople>> findAllByPost_Id(Integer post_id);
}
