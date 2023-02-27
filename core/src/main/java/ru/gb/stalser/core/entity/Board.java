package ru.gb.stalser.core.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

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

    @ElementCollection
    @CollectionTable(
            name = "boards_users_roles",
            joinColumns = @JoinColumn(name = "board_id")
    )
    @ToString.Exclude
    private Set<BoardUserRole> boardUserRoles = new HashSet<>();

}
