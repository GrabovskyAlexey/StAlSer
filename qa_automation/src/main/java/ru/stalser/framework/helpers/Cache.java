package ru.stalser.framework.helpers;

import ru.gb.stalser.api.dto.auth.AuthRequest;

import java.util.Objects;

import static ru.stalser.framework.steps.rests.StalserRestSteps.doPostToAuthUser;

public class Cache {

    private static String token = null;

    public static String getToken() {

        if (Objects.isNull(token)) {

            AuthRequest authRequest = new AuthRequest();
            authRequest.setLogin("autotest_user");
            authRequest.setPassword("autotestpswd");

            token = doPostToAuthUser(authRequest).getToken();
        }

        return token;
    }
}
