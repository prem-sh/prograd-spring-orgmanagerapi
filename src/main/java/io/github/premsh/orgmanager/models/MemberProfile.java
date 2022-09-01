package io.github.premsh.orgmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(
        name = "member_profile",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "organization_id", "role_id"})}
)
public class MemberProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    public void removeUser(){
        if(user!=null) user.removeMember(this);
    }

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    public void removeOrg(){
        if(organization!=null) organization.removeMember(this);
    }

    @NotNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;
    public void removeDesignation(){
        if(designation!=null) {
            designation.removeMember(this);
        }
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    public void removeDepartment(){
        if(department!=null) {
            department.removeMember(this);
        }
    }

    @JsonIgnoreProperties(value = {"memberProfile"})
    @OneToOne
    @JoinColumn(name = "salary_profile", referencedColumnName = "id")
    private Payroll payroll;

    @Column(name = "pan_number", length = 50)
    private String panNumber;

    @Column(name = "account_number", length = 50)
    private String bankAccountNumber;

    @Column(name = "ifsc", length = 50)
    private String ifsc;

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
