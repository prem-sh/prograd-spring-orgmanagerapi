package io.github.premsh.orgmanager.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="employee_salary_slip")
public class Salaryslip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Employee.class, cascade =CascadeType.ALL)
    private Employee employee;

    @ManyToOne(targetEntity = Payroll.class, cascade =CascadeType.ALL)
    private Payroll payroll;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "slip_url")
    private String slipUrl;

    @Column(name = "is_slip_generated")
    private Boolean slipGenerated = false;

    @Column(name = "is_slip_delivered")
    private Boolean slipDelivered = false;

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
