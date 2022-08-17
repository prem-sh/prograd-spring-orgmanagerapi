package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    @Query("""
            select a from Attendance a
            where a.employee.organization.id = ?1 and a.employee.id = ?2 and a.isDeleted = false""")
    List<Attendance> findByEmployeeId(long id, long id1);

    @Query("select a from Attendance a where a.employee.organization.id = ?1 and a.isDeleted = false")
    List<Attendance> findAll(long id);

    @Query("""
            select (count(a) > 0) from Attendance a
            where a.employee.organization.id = ?1 and a.id = ?2 and a.isDeleted = false""")
    boolean existsById(long id, long id1);

    @Query("select a from Attendance a where a.employee.organization.id = ?1 and a.id = ?2")
    Optional<Attendance> findById(long id, long id1);
    
    

    
}
