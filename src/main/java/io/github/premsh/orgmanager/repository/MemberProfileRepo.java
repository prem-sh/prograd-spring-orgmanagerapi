package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberProfileRepo extends JpaRepository<MemberProfile, Long> {
    
    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.isDeleted = false")
    List<MemberProfile> findAll(long id);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.id = ?2")
    Optional<MemberProfile> findById(long id, Long id1);

    @Query("""
            select (count(m) > 0) from MemberProfile m
            where m.organization.id = ?1 and m.id = ?2 and m.isDeleted = false""")
    boolean existById(long id, Long id1);

    @Query("select m from MemberProfile m where m.user.id = ?1 and m.isDeleted = false")
    List<MemberProfile> findByUserId(long id);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and m.user.id = ?2 and m.isDeleted = false")
    Optional<MemberProfile> findByOrgIdUserId(long orgId, long usrId);

    @Query("select (count(m) > 0) from MemberProfile m where m.user.id = ?1 and m.id = ?2 and m.isDeleted = false")
    boolean existsByUserId(long id, Long id1);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and id = ?2 and m.role.roleName = ?3 and m.isDeleted = false""")
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
            where m.organization.id = ?1 and upper(m.user.firstName) like upper(concat('%', ?2,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterFirstname(long id, String firstName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and upper(m.user.lastName) like upper(concat('%', ?2,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterLastname(long id, String lastName);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and upper(m.user.phone) like upper(concat('%', ?2,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterPhone(long id, String phone);

    @Query("""
            select m from MemberProfile m
            where m.organization.id = ?1 and upper(m.user.address) like upper(concat('%', ?2,'%')) and m.isDeleted = false""")
    List<MemberProfile> filterAddress(long id, String address);

    @Query("select m from MemberProfile m where m.organization.id = ?1 and upper(m.user.email) like upper(concat('%', ?2,'%')) and m.isDeleted = false")
    List<MemberProfile> filterEmail(long id, String email);

}
