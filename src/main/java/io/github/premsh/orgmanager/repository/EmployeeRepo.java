package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.organization.id = ?1 and e.isDeleted = false")
    List<Employee> findAll(long id);

    @Query("select e from Employee e where e.organization.id = ?1 and e.id = ?2")
    Optional<Employee> findById(long id, long id1);

    @Query("""
            select e from Employee e
            where e.organization.id = ?1 and upper(e.firstName) like upper(concat('%', ?2,'%')) and e.isDeleted = false""")
    List<Employee> filterFirstname(long id, String firstName);

    @Query("""
            select e from Employee e
            where e.organization.id = ?1 and upper(e.lastName) like upper(concat('%', ?2,'%')) and e.isDeleted = false""")
    List<Employee> filterLastname(long id, String lastName);

    @Query("""
            select e from Employee e
            where e.organization.id = ?1 and upper(e.email) like upper(concat('%', ?2,'%')) and e.isDeleted = false""")
    List<Employee> filterEmail(long id, String email);

    @Query("""
            select e from Employee e
            where e.organization.id = ?1 and upper(e.address) like upper(concat('%', ?2,'%')) and e.isDeleted = false""")
    List<Employee> filterAddress(long id, String address);

    @Query("""
            select e from Employee e
            where e.organization.id = ?1 and upper(e.phone) like upper(concat('%', ?2,'%')) and e.isDeleted = false""")
    List<Employee> filterPhone(long id, String phone);

    @Query("select (count(e) > 0) from Employee e where e.organization.id = ?1 and e.id = ?2 and e.isDeleted = false")
    boolean existsById(long id, long id1);


}
