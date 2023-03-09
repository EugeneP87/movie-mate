package ru.yandex.practicum.filmorate.controller;

class UserControllerTest {
/*
    UserController userController = new UserController(new UserService(new InMemoryUserStorage()));

    @Test()
    void checkUserEmail() {
        Assertions.assertThrows(ValidationException.class, () ->
                userController.create(User.builder()
                        .id(1)
                        .email("mailyandex.ru")
                        .login("Login")
                        .name("Name")
                        .birthday(LocalDate.EPOCH)
                        .build())
        );
    }

    @Test()
    void userLoginNotBlankOrSpaces() {
        Assertions.assertThrows(ValidationException.class, () ->
                userController.create(User.builder()
                        .id(1)
                        .email("mail@yandex.ru")
                        .login(" ")
                        .name("Name")
                        .birthday(LocalDate.EPOCH)
                        .build())
        );
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
        Assertions.assertThrows(ValidationException.class, () ->
                userController.create(User.builder()
                        .id(1)
                        .email("mail@yandex.ru")
                        .login("Login")
                        .name("Name")
                        .birthday(LocalDate.now().plusDays(1))
                        .build())
        );
    }*/
}