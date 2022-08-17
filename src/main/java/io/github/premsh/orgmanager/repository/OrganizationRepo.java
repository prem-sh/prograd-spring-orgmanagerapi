package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {
    @Override
    @Query("select o from Organization o where o.isDeleted = false")
    List<Organization> findAll();

    @Query("select (count(o) > 0) from Organization o where o.id = ?1 and o.isDeleted = false")
    boolean existsById(long id);

}
