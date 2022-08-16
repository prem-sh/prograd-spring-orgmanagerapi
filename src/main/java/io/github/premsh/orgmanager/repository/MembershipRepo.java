package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepo extends JpaRepository<Membership, Long> {
    @Query("select m from Membership m where m.user.id = ?1")
    List<Membership> findByUserId(long id);

}
