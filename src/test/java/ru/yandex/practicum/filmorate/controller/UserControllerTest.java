package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;

class UserControllerTest {

    UserController userController = new UserController();

    @Test()
    void checkUserEmail() {
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(User.builder()
                    .id(1)
                    .email("mailyandex.ru")
                    .login("Login")
                    .name("Name")
                    .birthday(LocalDate.EPOCH)
                    .build());
        });
    }

    @Test()
    void userLoginNotBlankOrSpaces() {
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(User.builder()
                    .id(1)
                    .email("mail@yandex.ru")
                    .login(" ")
                    .name("Name")
                    .birthday(LocalDate.EPOCH)
                    .build());
        });
    }

    @Test()
    void checkUserNameWhenEmpty() {
        User user = userController.create(User.builder()
                .id(1)
                .email("mail@yandex.ru")
                .login("Login")
                .birthday(LocalDate.EPOCH)
                .build());
        Assertions.assertEquals(user.getLogin(), user.getName());
    }

    @Test()
    void checkUserBirthdayNotInFuture() {
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(User.builder()
                    .id(1)
                    .email("mail@yandex.ru")
                    .login("Login")
                    .name("Name")
                    .birthday(LocalDate.now().plusDays(1))
                    .build());
        });
    }

}