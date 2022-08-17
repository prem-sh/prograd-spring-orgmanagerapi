package io.github.premsh.orgmanager.models;

import io.github.premsh.orgmanager.constants.ValidationMessage;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 50)
    @NotBlank
    @Column(name = "name", length = 50, nullable = false)
    private String name;


    @Column(name = "website")
    private String website;

    @Column(name = "address")
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
