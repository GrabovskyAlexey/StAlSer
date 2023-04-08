package ru.gb.stalser.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.stalser.core.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, String> {

}
