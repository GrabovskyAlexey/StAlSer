package ru.gb.stalser.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.gb.stalser.core.utils.DefaultInstantDeserializer;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;



@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDescription;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonSerialize(using= InstantSerializer.class)
    @JsonDeserialize(using= DefaultInstantDeserializer.class)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonSerialize(using= InstantSerializer.class)
    @JsonDeserialize(using= DefaultInstantDeserializer.class)
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    @ToString.Exclude
    private List<User> users;

    public Role(final String roleName, final String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }
}

