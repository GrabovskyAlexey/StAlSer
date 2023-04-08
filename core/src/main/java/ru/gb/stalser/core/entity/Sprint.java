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
@Table(name = "sprints")
@Getter
@Setter
@ToString
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
    @JsonSerialize(using= InstantSerializer.class)
    @JsonDeserialize(using= DefaultInstantDeserializer.class)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonSerialize(using= InstantSerializer.class)
    @JsonDeserialize(using= DefaultInstantDeserializer.class)
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @ToString.Exclude
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
