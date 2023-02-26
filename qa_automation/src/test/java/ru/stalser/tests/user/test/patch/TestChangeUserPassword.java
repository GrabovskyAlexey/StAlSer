package ru.stalser.tests.user.test.patch;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.stalser.TestBase;
import ru.stalser.iterators.UserTestIterator;

@Epic("Stalser")
@Feature("Пользователи")
@Story("Смена пароля юзера - позитив")
@Tag("Stalser.User")
public class TestChangeUserPassword extends TestBase {


    @Test
    @DisplayName("01. Смена пароля юзера")
    @Description("Запрос на аутентификацию юзера в Stalser")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("BLOCKER")
    public void testChangeUserPassword() {

        UserTestIterator.instance()
                .userCreateIterating()
                /* Логинимся созданным юзером */
                .userAuthIterating()
                /* Смена пароля юзеру */
                .userChangePasswordIterating("newgrafpswd")
                .withAfterTestDeletingUser()
                /* Логинимся с новым паролем */
                .userAuthIterating();
    }
}
