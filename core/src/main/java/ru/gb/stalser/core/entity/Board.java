package ru.gb.stalser.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import ru.gb.stalser.core.utils.DefaultInstantDeserializer;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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
    @JsonSerialize(using= InstantSerializer.class)
    @JsonDeserialize(using= DefaultInstantDeserializer.class)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonSerialize(using= InstantSerializer.class)
    @JsonDeserialize(using= DefaultInstantDeserializer.class)
    private Instant updatedAt;
    @Column(name = "board_alias")
    private String boardAlias;
    @Column(name = "is_active")
    private Boolean isActive;

    @CreatedBy
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

}
