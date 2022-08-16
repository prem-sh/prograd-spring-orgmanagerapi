package io.github.premsh.orgmanager.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="user")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private boolean isEnabled = true;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

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
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {//need to change =========
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
        public boolean isEnabled() {
        return this.isEnabled;
    }

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.EAGER
    )
    private Set<Membership> memberships = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = memberships.stream().map((mem) -> new SimpleGrantedAuthority(mem.getRole().getRoleName())).collect(Collectors.toList());
        System.out.println(Arrays.toString(authorities.toArray()));
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User usr = (User) o;
        return Objects.equals(id, usr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

}

