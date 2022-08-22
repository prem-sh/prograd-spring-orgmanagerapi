package io.github.premsh.orgmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.premsh.orgmanager.services.PrincipalService;
import io.github.premsh.orgmanager.services.PrincipalServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(
        name="department",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"department_name", "organization_id"})}
)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @NotNull
    @Size(max = 50)
    @Column(name = "department_name")
    private String departmentName;

    @JsonManagedReference
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private List<MemberProfile> memberProfiles;

    public void removeMember(MemberProfile memberProfile){
        this.memberProfiles.remove(memberProfile);
        memberProfile.setDepartment(null);
    }


    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "created_by", updatable = false, nullable = false)
    private Long createdBy;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @PrePersist
    private void onCreate(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    @PreUpdate
    private void onUpdate(){
        this.updatedAt = new Date();
    }
}
