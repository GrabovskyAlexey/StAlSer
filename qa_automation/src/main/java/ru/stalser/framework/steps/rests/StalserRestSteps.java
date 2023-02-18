package ru.stalser.framework.steps.rests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import ru.stalser.framework.helpers.SoftAssertHelper;

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

        SoftAssertHelper.softly().assertThat(response.extract().body().asPrettyString()).as("тело ответа").isEmpty();
    }
}
