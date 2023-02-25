package ru.stalser.tests.user.methods;

import io.qameta.allure.Step;
import org.assertj.core.data.TemporalUnitWithinOffset;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.stalser.framework.autotestobjects.RegisterRequestDto;
import ru.stalser.framework.helpers.DBHelper;
import ru.stalser.framework.helpers.ParseHelper;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static ru.stalser.framework.helpers.SoftAssertHelper.softly;

public class UserMethods {

    public static void assertUserCreationInTables(RegisterRequestDto registerRequestDto) {

        RegisterRequest registerRq = registerRequestDto.getRegisterRequest();

        Map<String, String> userData = getUserInfoFromDBByLogin(registerRq.getLogin());

        softly().assertThat(userData.get("id")).as("user id").isNotNull();
        softly().assertThat(userData.get("email")).as("user email").isEqualTo(registerRq.getEmail());
        softly().assertThat(userData.get("login")).as("user login").isEqualTo(registerRq.getLogin());
        softly().assertThat(userData.get("password")).as("user password").isNotNull();
        softly().assertThat(userData.get("activation_code")).as("user activation_code").isNotNull();
        softly().assertThat(userData.get("is_enabled")).as("user is_enabled").isEqualToIgnoringCase("t");
        softly().assertThat(userData.get("is_activated")).as("user is_activated").isEqualToIgnoringCase("f");
        softly().assertThat(userData.get("role_name")).as("user role").isEqualToIgnoringCase("ROLE_USER");
        softly().assertThat(ParseHelper.parseDateTimeFromDBToLocalDateTime(userData.get("created_at"))).as("user created at")
                .isCloseTo(registerRequestDto.getCreationTime(), new TemporalUnitWithinOffset(4, ChronoUnit.SECONDS));
        softly().assertThat(ParseHelper.parseDateTimeFromDBToLocalDateTime(userData.get("updated_at"))).as("user updated_at")
                .isCloseTo(registerRequestDto.getCreationTime(), new TemporalUnitWithinOffset(4, ChronoUnit.SECONDS));

        /* Удаляем созданного юзера из таблиц users_roles и users */
        deleteUserFromTablesByLogin(registerRq.getLogin());
    }

    @Step("Удаление юзера из таблиц users_roles и users по логину {userLogin}")
    public static void deleteUserFromTablesByLogin(String userLogin) {

        String sql = String.format("BEGIN;\n" +
                        "delete from users_roles where users_id =\n" +
                        "(select id from users where login = '%1$s');\n" +
                        "delete from users where login = '%1$s';\n" +
                        "END;",
                userLogin);

        DBHelper dbHelper = new DBHelper();
        dbHelper.connectStalser();
        dbHelper.executeUpdate(sql, "делаю запрос в таблицу Users на удаление юзера по логину = " + userLogin);
        dbHelper.disconnect();
    }

    @Step("Получение данных о юзере из таблиц users_roles и users по логину {userLogin}")
    public static Map<String, String> getUserInfoFromDBByLogin(String userLogin) {

        String sql = String.format("select us.*, r.role_name from users us\n" +
                        "join users_roles ur on us.id = ur.users_id\n" +
                        "join roles r on ur.roles_id = r.id\n" +
                        "where us.login = '%s'",
                userLogin);

        DBHelper dbHelper = new DBHelper();
        dbHelper.connectStalser();
        Map<String, String> resultMap = dbHelper.doRequestInToMap(sql, "делаю запрос в БД для получения информации о юзере по логину = " + userLogin);
        dbHelper.disconnect();

        return resultMap;
    }

    @Step("Получение юзеров из таблицы users")
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
