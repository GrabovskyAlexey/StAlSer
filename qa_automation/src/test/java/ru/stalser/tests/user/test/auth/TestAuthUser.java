package ru.stalser.tests.user.test.auth;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.stalser.TestBase;
import ru.stalser.iterators.UserTestIterator;

@Epic("Stalser")
@Feature("Пользователи")
@Story("Аутентификация юзера - позитив")
@Tag("Stalser.User")
public class TestAuthUser extends TestBase {


    @Test
    @DisplayName("01. Аутентификация юзера")
    @Description("Запрос на аутентификацию юзера в Stalser")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("BLOCKER")
    public void testAuthUser() {

        UserTestIterator.instance()
                .userCreateIterating()
                /* Логинимся созданным юзером */
                .withAfterTestDeletingUser()
                .userAuthIterating();
    }
}
