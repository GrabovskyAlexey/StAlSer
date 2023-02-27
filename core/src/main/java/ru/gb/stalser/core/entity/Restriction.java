package ru.gb.stalser.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "restrictions")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Restriction extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "restriction_name")
    private String restrictionName;
    @Column(name = "restriction_desc")
    private String restrictionDesc;

}
