package ru.gb.stalser.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "sprints")
@Getter
@Setter
@RequiredArgsConstructor
public class Sprint {

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

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final Sprint sprint = (Sprint) o;
        return id != null && Objects.equals(id, sprint.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
