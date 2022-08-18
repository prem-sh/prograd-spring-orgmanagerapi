package io.github.premsh.orgmanager.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "member_profile")
public class MemberProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    private Organization organization;

    @NotNull
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    private Role role;

    @ManyToOne(targetEntity = Designation.class, fetch = FetchType.EAGER)
    private Designation designation;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    private Department department;

    @ManyToOne(targetEntity = Payroll.class, fetch = FetchType.EAGER)
    private Payroll payroll;

    @Column(name = "pan_number", length = 50)
    private String panNumber;

    @Column(name = "account_number", length = 50)
    private String bankAccountNumber;

    @Column(name = "ifsc", length = 50)
    private String ifsc;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User updatedBy;

    @PrePersist
    private void onCreate(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    @PreUpdate
    private void onUpdate(){
        this.updatedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MemberProfile that = (MemberProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }

}
