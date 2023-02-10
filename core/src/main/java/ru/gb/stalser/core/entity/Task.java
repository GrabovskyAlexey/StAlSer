package ru.gb.stalser.core.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.gb.stalser.api.dto.task.TaskPriority;
import ru.gb.stalser.api.dto.task.TaskStatus;
import ru.gb.stalser.api.dto.task.TaskType;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Data
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "deadline")
    private Instant deadline;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assigneeId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creatorId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name ="tasks_sprints",
            joinColumns = @JoinColumn(name = "tasks_id"),
            inverseJoinColumns = @JoinColumn(name = "sprints_id")
    )
    @ToString.Exclude
    private List<Sprint> sprints;


    @ManyToMany
    @JoinTable(
            name ="tags_tasks",
            joinColumns = @JoinColumn(name = "tasks_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @ToString.Exclude
    private List<Tag> tags;



}
