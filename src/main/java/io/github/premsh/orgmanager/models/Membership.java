package io.github.premsh.orgmanager.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    private Organization organization;
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Membership that = (Membership) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
