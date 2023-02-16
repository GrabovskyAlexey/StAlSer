package ru.gb.stalser.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.gb.stalser.api.dto.invite.InviteStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "invites")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Invite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @ToString.Exclude
    private Board board;

}
