package ru.stalser.tests.user.test.creation;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.stalser.TestBase;
import ru.stalser.iterators.UserTestIterator;

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

        UserTestIterator.instance()
                .withAfterTestDeletingUser()
                .userCreateIterating();
    }
}
