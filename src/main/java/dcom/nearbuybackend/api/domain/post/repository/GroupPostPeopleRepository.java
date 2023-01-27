package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPostPeopleRepository extends JpaRepository<GroupPostPeople, Integer> {
}
