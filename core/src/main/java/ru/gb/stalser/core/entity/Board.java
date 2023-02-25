package ru.gb.stalser.core.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "boards")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "board_name")
    private String boardName;
    @Column(name = "board_desc")
    private String boardDesc;
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column(name = "board_alias")
    private String boardAlias;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    @ToString.Exclude
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "users_boards",
            joinColumns = @JoinColumn(name = "boards_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    @ToString.Exclude
    private List<User> users;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @MapKeyJoinColumn(name = "user_id")
    @JoinTable(
            name = "board_binding_board_role_binding_user",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "board_role_id")
    )
    private Map<User, BoardRole> usersWithRoles = new HashMap<>();

}
