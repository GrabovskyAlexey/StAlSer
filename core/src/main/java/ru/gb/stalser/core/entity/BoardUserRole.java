package ru.gb.stalser.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUserRole {
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false, updatable = false
    )
    protected User user;
    @ManyToOne
    @JoinColumn(
            name = "board_role_id",
            nullable = false,
            updatable = false
    )
    protected BoardRole boardRole;
}
