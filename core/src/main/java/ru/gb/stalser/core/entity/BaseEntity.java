package ru.gb.stalser.core.entity;

import lombok.*;

import java.util.Objects;


@Getter
@Setter
public abstract class BaseEntity {
    private Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        return Objects.equals(id, other.getId());
    }
}
