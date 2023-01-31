package ru.gb.stalser.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.gb.stalser.api.dto.invite.InviteStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "invites")
@Getter
@Setter
@NoArgsConstructor
public class Invite {
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
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invite invite = (Invite) o;
        return id.equals(invite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
