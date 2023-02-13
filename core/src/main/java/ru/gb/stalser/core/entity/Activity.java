package ru.gb.stalser.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "task_activities")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Activity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "activity_info")
    private String activityInfo;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;


}
