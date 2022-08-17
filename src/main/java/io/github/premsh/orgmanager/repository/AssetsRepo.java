package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetsRepo extends JpaRepository<Asset, Long> {
    @Query("select a from Asset a where a.organization.id = ?1 and a.id = ?2")
    Optional<Asset> findById(long id, long id1);

    @Query("select a from Asset a where a.organization.id = ?1 and a.isDeleted = false")
    List<Asset> findAll(long id);

    @Query("""
            select (count(a) > 0) from Asset a
            where a.organization.id = ?1 and a.id = ?2 and a.organization.isDeleted = false""")
    boolean existsById(long id, long id1);

    @Query("select a from Asset a where upper(a.name) like upper(concat('%', ?1,'%'))")
    List<Asset> filter(String name);

}
