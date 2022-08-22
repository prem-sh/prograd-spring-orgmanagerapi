package io.github.premsh.orgmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @NotNull
    @ManyToOne
    private Organization organization;

    @OneToOne(mappedBy = "payroll")
    private MemberProfile memberProfile;

    @PreRemove
    private void preRemove() {
        if(memberProfile!=null) memberProfile.setPayroll(null);
    }

    @NotNull
    private Double basicPay = 0D;
    private Double conveyanceAllowance;
    private Double medicalAllowance;
    private Double houseRentAllowance;
    private Double foodAllowance;
    private Double professionalTax;
    private Double providentFund;
    private Double employeeStateInsurance;

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
