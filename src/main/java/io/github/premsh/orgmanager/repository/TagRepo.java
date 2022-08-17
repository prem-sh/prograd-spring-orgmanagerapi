package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.organization.id = ?1 and t.id = ?2")
    Optional<Tag> findById(long id, long id1);

    @Query("select t from Tag t where t.organization.id = ?1 and t.isDeleted = false")
    List<Tag> findAll(long id);

    @Query("select t from Tag t where t.organization.id = ?1 and upper(t.tag) like upper(concat('%', ?2,'%')) and t.isDeleted = false")
    List<Tag> filter(long id, String tag);

    @Query("select (count(t) > 0) from Tag t where t.organization.id = ?1 and t.id = ?2 and t.isDeleted = false")
    boolean existsById(long id, long id1);


}
