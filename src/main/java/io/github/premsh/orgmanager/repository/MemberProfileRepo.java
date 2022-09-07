package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberProfileRepo extends JpaRepository<MemberProfile, Long> {
    
    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.isDeleted = false")
    List<MemberProfile> findAll(long id);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.id = ?2")
    Optional<MemberProfile> findById(long id, Long id1);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.payroll.id = ?2")
    List<MemberProfile> findMembersByPayrollId(long id, long id1);

    @Query("""
            select (count(m) > 0) from MemberProfile m
            where m.organization.id = ?1 and m.id = ?2 and m.isDeleted = false""")
    boolean existById(long id, Long id1);

    @Query("select m from MemberProfile m where m.user.id = ?1 and m.isDeleted = false")
    List<MemberProfile> findByUserId(long id);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.user.id = ?2 and m.isDeleted = false")
    Optional<MemberProfile> findByOrgIdUserId(long orgId, long usrId);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.id = ?2 and m.role.roleName = ?3 and m.isDeleted = false""")
    Optional<MemberProfile> findByOrgIdUserIdRoleId(long orgId, long userId, String roleName);

    @Query("""
            select (count(m) > 0) from MemberProfile m
            where m.organization.id = ?1 and m.user.id = ?2 and m.isDeleted = false""")
    boolean existByOrgIdUserId(long orgId, long userId);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName = ?2 and m.isDeleted = false""")
    List<MemberProfile> findByOrgIdRoleName(long orgId, String roleName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.user.firstName) like upper(concat('%', ?3,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterEmployeeFirstname(long id, String roleName, String firstName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.user.lastName) like upper(concat('%', ?3,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterEmployeeLastname(long id, String roleName, String lastName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.user.phone) like upper(concat('%', ?3,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterEmployeePhone(long id, String roleName, String phone);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.user.address) like upper(concat('%', ?3,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterEmployeeAddress(long id, String roleName, String address);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.user.email) like upper(concat('%', ?3,'%')) and m.isDeleted = false")
    List<MemberProfile> filterEmployeeEmail(long id, String roleName, String email);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and upper(m.department.departmentName) like upper(?2) and m.isDeleted = false""")
    List<MemberProfile> findMemberByDepartmentName(long id, String departmentName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and upper(m.designation.designationName) like upper(?2) and m.isDeleted = false""")
    List<MemberProfile> findMemberByDesignationName(long id, String designationName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and upper(m.role.roleName) like upper(?2) and m.isDeleted = false""")
    List<MemberProfile> findMemberByRoleName(long id, String roleName);

    @Override
    @Transactional
    @Modifying
    @Query("delete from MemberProfile m where m.id = ?1")
    void deleteById(Long id);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName = ?2 and  upper(m.designation.designationName) like upper(concat('%', ?3,'%')) and m.isDeleted = false""")
    List<MemberProfile> findByOrgIdRoleNameDesignation(long id, String roleName, String designationName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.department.departmentName) like upper(concat('%', ?3,'%')) and m.isDeleted = false""")
    List<MemberProfile> findByOrgIdRoleNameDepartment(long id, String roleName, String departmentName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and m.role.roleName like ?2 and upper(m.designation.designationName) like upper(concat('%', ?3,'%')) and upper(m.department.departmentName) like upper(concat('%', ?4,'%')) and m.isDeleted = false""")
    List<MemberProfile> getEmployeesByDesignationDepartment(long id, String roleName, String designationName, String departmentName);

    @Query("""
            select (count(m) > 0) from MemberProfile m
            where m.organization.id = ?1 and upper(m.user.email) like upper(?2)""")
    boolean existByEmail(long id, String email);


}
