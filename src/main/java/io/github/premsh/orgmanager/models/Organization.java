package io.github.premsh.orgmanager.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.premsh.orgmanager.constants.ValidationMessage;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Data
@Entity
@Table(name = "organization")

public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 50)
    @NotBlank
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @URL
    @Column(name = "website")
    private String website;

    @Column(name = "address")
    private String address;

    @NotNull
    @Size(max = 15, message = ValidationMessage.PHONE_LENGTH_CONSTRAINT)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = ValidationMessage.PHONE_PATTERN_CONSTRAINT )
    @Column(name = "phone", length = 15, nullable = false, unique = true)
    private String phone;

    @Email
    @NotNull
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

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

    //----------Associations--------------
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Asset> assets;

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Department> departments;

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Designation> designations;

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<MemberProfile> memberProfiles;
    public void removeMember(MemberProfile memberProfile){
        this.memberProfiles.remove(memberProfile);
        memberProfile.setOrganization(null);
    }

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Payroll> payrolls;

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Tag> tags;

    //-=----------------------

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
