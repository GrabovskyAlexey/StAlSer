package ru.stalser.tests.user.test.creation;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.stalser.TestBase;

import static ru.stalser.framework.steps.rests.StalserRestSteps.doPostToCreateUser;

@Epic("Stalser")
@Feature("Пользователи")
@Story("Создание юзера - позитив")
@Tag("Stalser.User")
public class TestCreationUser extends TestBase {


    @Test
    @DisplayName("01. Создание юзера")
    @Description("TODO")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("BLOCKER")
    public void testCreateUser() {

        RegisterRequest registerRq = createDefaultRegisterRequest();
        System.out.println(registerRq);

        doPostToCreateUser(registerRq);
    }



    public static RegisterRequest createDefaultRegisterRequest() {

        return new RegisterRequest()
                .setLogin("grafwwwolf")
                .setPassword("grafpswd")
                .setEmail("grafwwwolf@mail.ru");
    }
}
