package ru.gb.stalser.core.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.gb.stalser.core.utils.DefaultInstantDeserializer;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;


@Entity
@Table(name = "task_comments")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @ToString.Exclude
    private Task task;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private User author;

    @Column(name = "comment_text")
    private String commentText;

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


}