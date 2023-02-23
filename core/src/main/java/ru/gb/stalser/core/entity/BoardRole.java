package ru.gb.stalser.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board_roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_desc")
    private String roleDesc;

    @ManyToMany
    @JoinTable(
            name = "board_roles_binding_restriction",
            joinColumns = @JoinColumn(name = "board_role_id"),
            inverseJoinColumns = @JoinColumn(name = "restriction_id")
    )
    @ToString.Exclude
    private List<Restriction> restrictions;
}
