package io.github.premsh.orgmanager.models;

import io.github.premsh.orgmanager.constants.ValidationMessage;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    private Organization organization;

    @NotNull
    @Size(max = 50)
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "lastname", nullable = true)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Email
    @NotNull
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @ManyToOne(targetEntity = Designation.class, fetch = FetchType.EAGER)
    private Designation designation;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER)
    private Department department;

    @ManyToOne(targetEntity = Payroll.class, fetch = FetchType.EAGER)
    private Payroll payroll;

    @Column(name = "pan", length = 50)
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
}
