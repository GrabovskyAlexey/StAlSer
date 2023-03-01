package ru.stalser.framework.testdata;

import ru.gb.stalser.api.dto.user.UserDto;

public class TestUsers {

    public static UserDto AUTOTEST_USER = UserDto.builder()
            .id(5L)
            .login("autotest_user")
            .email("autotest@mail.ru")
            .build();
}
