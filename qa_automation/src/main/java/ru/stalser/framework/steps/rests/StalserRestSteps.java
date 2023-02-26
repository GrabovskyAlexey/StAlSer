package ru.stalser.framework.steps.rests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.stalser.framework.autotestobjects.RegisterRequestDto;

import static ru.stalser.framework.constants.Hosts.BASE_HOST;
import static ru.stalser.framework.helpers.SoftAssertHelper.softly;

@Slf4j
public class StalserRestSteps {

    @Step("Делаю POST запрос на что-то")
    public static void doPostSomeDraft(Object someObjectToPost) {

        ValidatableResponse response = RestAssured
                .given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "token")
                .params("", "")
                .body(someObjectToPost)
                .when().log().all()
                .post("someHostName")
                //Получен ответ от сервера
                .then().log().all();

        response.assertThat().statusCode(HttpStatus.SC_CREATED);

        softly().assertThat(response.extract().body().asPrettyString()).as("тело ответа").isEmpty();
    }

    @Step("Делаю POST запрос на создание нового юзера Stalser")
    public static RegisterRequestDto doPostToCreateUser(RegisterRequest registerRq) {

        ValidatableResponse response = RestAssured
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(registerRq)
                .when()
                .post(BASE_HOST + "/register")
                //Получен ответ от сервера
                .then();

        softly().assertThat(response.extract().statusCode()).as("response code").isEqualTo(HttpStatus.SC_CREATED);
//        softly().assertThat(response.extract().statusCode()).as("response code").isEqualTo(HttpStatus.SC_OK);
//        if (softly().wasSuccess()) {      //TODO раскомментить после фикса баги с ответом 200

            softly().assertThat(response.extract().body().asPrettyString()).as("тело ответа").isNotEmpty();
            AuthResponse authResponse = response.extract().body().as(AuthResponse.class);
            softly().assertThat(authResponse.getToken()).as("Token").isNotNull();
            softly().assertThat(authResponse.getRefreshToken()).as("Refresh Token").isNotNull();

            RegisterRequestDto registerRequestDto = RegisterRequestDto.of(registerRq);
            registerRequestDto.setToken(authResponse.getToken());
            registerRequestDto.setRefreshToken(authResponse.getRefreshToken());
            return registerRequestDto;
//        }
//                            //TODO раскомментить после фикса баги с ответом 200
//        return null;
    }

    @Step("Делаю POST запрос на аутентификацию юзера Stalser")
    public static AuthResponse doPostToAuthUser(AuthRequest authRequest) {

        ValidatableResponse response = RestAssured
                .given()
                .contentType("application/json; charset=UTF-8")
                .body(authRequest)
                .when()
                .post(BASE_HOST + "/auth")
                //Получен ответ от сервера
                .then();

        response.assertThat().statusCode(HttpStatus.SC_OK);

        softly().assertThat(response.extract().body().asPrettyString()).as("тело ответа").isNotEmpty();
        AuthResponse authResponse = response.extract().body().as(AuthResponse.class);
        softly().assertThat(authResponse.getToken()).as("Token").isNotNull();
        softly().assertThat(authResponse.getRefreshToken()).as("Refresh Token").isNotNull();

        return response.extract().body().as(AuthResponse.class);
    }

    @Step("Делаю POST запрос на изменение юзера Stalser")
    public static RegisterRequestDto doPostToChangePasswordUser(RegisterRequestDto registerRequestDto) {

        ValidatableResponse response = RestAssured
                .given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + registerRequestDto.getToken())
                .body(registerRequestDto.getAuthRequestPassUpdate())
                .when().log().all()
                .post(BASE_HOST + "/password/change")
                //Получен ответ от сервера
                .then().log().all();

        softly().assertThat(response.extract().statusCode()).as("response code").isEqualTo(HttpStatus.SC_OK);

        softly().assertThat(response.extract().body().asPrettyString()).as("тело ответа").isNotEmpty();
        AuthResponse authResponse = response.extract().body().as(AuthResponse.class);
        softly().assertThat(authResponse.getToken()).as("Token").isNotNull();
        softly().assertThat(authResponse.getRefreshToken()).as("Refresh Token").isNotNull();

        registerRequestDto.setToken(authResponse.getToken());
        registerRequestDto.setRefreshToken(authResponse.getRefreshToken());
        return registerRequestDto;
    }
}
