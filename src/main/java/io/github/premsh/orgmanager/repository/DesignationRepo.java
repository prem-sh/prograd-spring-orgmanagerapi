package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepo extends JpaRepository<Designation, Long> {
    @Query("select d from Designation d where d.organization.id = ?1 and d.isDeleted = false")
    List<Designation> findAll(long id);

    @Query("select d from Designation d where d.organization.id = ?1 and d.id = ?2 and d.isDeleted = false")
    Optional<Designation> findById(long id, long id1);

    @Query("select (count(d) > 0) from Designation d where d.organization.id = ?1 and d.id = ?2 and d.isDeleted = false")
    boolean existsById(long id, long id1);

    @Query("""
            select d from Designation d
            where d.organization.id = ?1 and d.designationName like ?2 and d.isDeleted = false""")
    List<Designation> filter(long id, String designationName);
    
    

}
