package io.github.premsh.orgmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @NotNull
    @Size(max = 50)
    @Column(name = "asset_name")
    private String name;

    @Column(name = "count")
    private long count = 0;

    @Column(name = "purchase_cost")
    private double purchaseCost;

    @Column(name = "current_value")
    private double currentValue;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "asset_tags",
            joinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )

    @JsonManagedReference
    private Set<Tag> tags = new HashSet<>();
    public void addTag(Tag tag){
        tags.add(tag);
    }
    public void removeTag(Tag tag){
        tags.remove(tag);
    }

    public void removeAllTags(){
        tags.clear();
    }
}
