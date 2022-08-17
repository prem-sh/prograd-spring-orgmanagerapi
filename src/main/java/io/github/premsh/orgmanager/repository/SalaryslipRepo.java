package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Salaryslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryslipRepo extends JpaRepository<Salaryslip, Long> {
    @Query("select s from Salaryslip s where s.employee.organization.id = ?1 and s.id = ?2")
    Optional<Salaryslip> findById(long id, long id1);

    @Query("select s from Salaryslip s where s.employee.organization.id = ?1 and s.isDeleted = false")
    List<Salaryslip> findAll(long id);

    @Query("""
            select s from Salaryslip s
            where s.employee.organization.id = ?1 and s.employee.id = ?2 and s.isDeleted = false""")
    List<Salaryslip> findAllByEmployeeId(long id, long id1);

    @Query("""
            select (count(s) > 0) from Salaryslip s
            where s.employee.organization.id = ?1 and s.id = ?2 and s.isDeleted = false""")
    boolean existsById(long id, long id1);
    
    
}
