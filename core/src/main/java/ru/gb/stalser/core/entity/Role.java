package ru.gb.stalser.core.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @JoinColumn(name = "role_name")
    private String roleName;

    @JoinColumn(name = "role_desc")
    private String roleDescription;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name ="users_roles",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    @ToString.Exclude
    private List<User> users;

    public Role(final String roleName, final String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
