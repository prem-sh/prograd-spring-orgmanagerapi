package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {
    @Query("select p from Payroll p where p.organization.id = ?1 and p.id = ?2")
    Optional<Payroll> findById(long id, long id1);

    @Query("select p from Payroll p where p.organization.id = ?1 and p.isDeleted = false")
    List<Payroll> findAll(long id);

    @Query("select (count(p) > 0) from Payroll p where p.organization.id = ?1 and p.id = ?2 and p.isDeleted = false")
    boolean existsById(long id, long id1);


    
}
