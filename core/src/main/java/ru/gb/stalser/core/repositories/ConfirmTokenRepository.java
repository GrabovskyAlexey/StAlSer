package ru.gb.stalser.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.stalser.core.entity.ConfirmToken;

@Repository
public interface ConfirmTokenRepository extends CrudRepository<ConfirmToken, String> {

}
