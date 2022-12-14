package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo  extends JpaRepository<Department, Long>{
    @Query("select d from Department d where d.organization.id = ?1 and d.isDeleted = false")
    List<Department> findAll(long id);

    @Query("select d from Department d where d.organization.id = ?1 and d.id = ?2 and d.isDeleted = false")
    Optional<Department> findById(long id, long id1);

    @Query("select (count(d) > 0) from Department d where d.organization.id = ?1 and d.id = ?2 and d.isDeleted = false")
    boolean existsById(long id, long id1);

    @Query("select d from Department d where d.organization.id = ?1 and d.departmentName like concat('%', ?2,'%') and d.isDeleted = false")
    List<Department> filter(long id, String departmentName);

    @Query("""
            select d from Department d
            where d.organization.id = ?1 and d.departmentName = ?2 and d.organization.isDeleted = false""")
    Optional<Department> findByName(long id, String departmentName);

    @Query("""
            select (count(d) > 0) from Department d
            where d.organization.id = ?1 and upper(d.departmentName) like upper(?2)""")
    boolean existsByDepartmentName(long id, String departmentName);


    
}
