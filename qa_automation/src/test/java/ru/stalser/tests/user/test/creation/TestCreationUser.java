package ru.stalser.tests.user.test.creation;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.stalser.TestBase;
import ru.stalser.framework.autotestobjects.RegisterRequestDto;

import static ru.stalser.framework.steps.rests.StalserRestSteps.doPostToCreateUser;
import static ru.stalser.tests.user.methods.UserMethods.assertUserCreationInTables;
import static ru.stalser.tests.user.methods.UserMethods.createDefaultRegisterRequest;

@Epic("Stalser")
@Feature("Пользователи")
@Story("Создание юзера - позитив")
@Tag("Stalser.User")
public class TestCreationUser extends TestBase {


    @Test
    @DisplayName("01. Создание юзера c ролью ROLE_USER")
    @Description("Регистрация нового юзера c ролью ROLE_USER в Stalser")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("BLOCKER")
    public void testCreateUserWithRoleUser() {

        /* Создание запроса на создание юзера */
        RegisterRequest registerRq = createDefaultRegisterRequest();

        /* Отправка запроса на создание юзера через API */
        RegisterRequestDto registerRequestDto = doPostToCreateUser(registerRq);

        /* Проверка создания юзера в таблицах users_roles и users */
        assertUserCreationInTables(registerRequestDto);
    }
}
