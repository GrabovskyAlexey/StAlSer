package ru.stalser.iterators;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthRequestPassUpdate;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.stalser.framework.autotestobjects.RegisterRequestDto;

import static ru.stalser.framework.steps.rests.StalserRestSteps.*;
import static ru.stalser.tests.user.methods.UserMethods.*;

@Data
@NoArgsConstructor(staticName = "instance")
public class UserTestIterator {

    private RegisterRequest registerRq = createDefaultRegisterRequest();

    private RegisterRequestDto registerRequestDto;

    private Boolean withDelete = false;

    public UserTestIterator withLogin(String login) {

        registerRq.setLogin(login);
        return this;
    }

    public UserTestIterator withPassword(String password) {

        registerRq.setPassword(password);
        return this;
    }

    public UserTestIterator withEmail(String email) {

        registerRq.setEmail(email);
        return this;
    }

    public UserTestIterator withAfterTestDeletingUser() {

        withDelete = true;
        return this;
    }

    public UserTestIterator userCreateIterating() {

        /* Отправка запроса на создание юзера через API */
        registerRequestDto = doPostToCreateUser(registerRq);

        /* Проверка создания юзера в таблицах users_roles и users */
        assertUserCreationInTables(registerRequestDto);

        deleteUserFromTablesIfItIsNeed();

        return this;
    }

    public UserTestIterator userAuthIterating() {

        /* Создание запроса аутентификации */
        AuthRequest authRequest = createDefaultAuthRequest(registerRq);

        /* Отправка запроса аутентификации юзера через API */
        AuthResponse authResponse = doPostToAuthUser(authRequest);
        registerRequestDto.setToken(authResponse.getToken());
        registerRequestDto.setRefreshToken(authResponse.getRefreshToken());

        deleteUserFromTablesIfItIsNeed();

        return this;
    }

    public UserTestIterator userChangePasswordIterating(String newPassword) {

        registerRequestDto.setAuthRequestPassUpdate(new AuthRequestPassUpdate());
        registerRequestDto.getAuthRequestPassUpdate().setOldPassword(registerRq.getPassword());
        registerRq.setPassword(newPassword);
        registerRequestDto.getAuthRequestPassUpdate().setNewPassword(registerRq.getPassword());

        /* Отправка запроса на изменение пароля юзера через API */
        registerRequestDto = doPostToChangePasswordUser(registerRequestDto);

        /* Проверка создания юзера в таблицах users_roles и users */
        assertUserCreationInTables(registerRequestDto);

        deleteUserFromTablesIfItIsNeed();

        return this;
    }

    private void deleteUserFromTablesIfItIsNeed() {

        if (withDelete) {

            deleteUserFromTablesByLogin(registerRq.getLogin());
        }
    }
}
