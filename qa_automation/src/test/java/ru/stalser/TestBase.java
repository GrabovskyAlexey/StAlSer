package ru.stalser;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.stalser.framework.helpers.AllureHelper;
import ru.stalser.framework.helpers.RestAssuredLoggingFilter;
import ru.stalser.framework.helpers.SoftAssertHelper;
import ru.stalser.framework.helpers.StepsHierarchy;
import ru.stalser.framework.utils.Props;

import java.util.Collections;

import static ru.stalser.framework.helpers.SoftAssertHelper.softly;

@Slf4j
public class TestBase {

    protected static final ThreadLocal<TestBase> thisTest = new ThreadLocal<>();

    public static TestBase getThisTest() {
        return thisTest.get();
    }

    @BeforeAll
    public static void beforeAll() {
        Props.init();
        RestAssured.reset();
        RestAssured.filters(Collections.singletonList(new RestAssuredLoggingFilter()));
        log.info(Thread.currentThread().getName() + ":: Инициализация завершена");
    }

    @BeforeEach
    public void beforeEach() {

        SoftAssertHelper.reset();
        thisTest.set(this);
        softly().setAfterAssertionErrorCollected(message -> AllureHelper.err(message.getMessage()));
    }

    @AfterEach
    public void afterEach() {

        try {
            if (!StepsHierarchy.get().getFinished()) {
                softly().assertAll();
                StepsHierarchy.get().finish();
            }
        } finally {
            StepsHierarchy.get().reset();
        }
    }

    @AfterAll
    public static void afterAll() {

//        unsubscribeAll();  TODO для кафки
    }

}
