package ru.stalser.tests.user.test.creation;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.stalser.TestBase;
import ru.stalser.framework.helpers.DBHelper;

import java.util.List;
import java.util.Map;

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

//        RegisterRequest registerRq = createDefaultRegisterRequest();
//        System.out.println(registerRq);
//
//        doPostToCreateUser(registerRq);

        System.out.println(getUsersFromDB());
    }


    public static List<Map<String, String>> getUsersFromDB() {

        String sql = "select * from users";

        DBHelper dbHelper = new DBHelper();
        dbHelper.connectStalser();
        List<Map<String, String>> resultList = dbHelper.doRequestInToList(sql, "делаю запрос в таблицу Users для получения всех юзеров");
        dbHelper.disconnect();

        return resultList;
    }

    public static RegisterRequest createDefaultRegisterRequest() {

        return new RegisterRequest()
                .setLogin("grafwwwolf")
                .setPassword("grafpswd")
                .setEmail("grafwwwolf@mail.ru");
    }
}
