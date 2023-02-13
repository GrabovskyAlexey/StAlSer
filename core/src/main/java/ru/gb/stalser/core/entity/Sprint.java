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
@Table(name = "sprints")
@Getter
@Setter
@NoArgsConstructor
public class Sprint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sprint_number")
    private Integer sprintNumber;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToMany
    @JoinTable(
            name ="tasks_sprints",
            joinColumns = @JoinColumn(name = "sprints_id"),
            inverseJoinColumns = @JoinColumn(name = "tasks_id")
    )
    @ToString.Exclude
    private List<Task> tasks;


}
