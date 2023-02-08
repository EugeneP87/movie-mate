package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidation {

    String message;

    public User checkUserCreation(User user) {
        if (user.getLogin().isBlank()) {
            message = "Логин не может быть пустым и содержать пробелы";
            log.debug(message);
            throw new ValidationException(message);
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            message = "Электронная почта не может быть пустой и должна содержать символ @";
            log.debug(message);
            throw new ValidationException(message);
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            message = "Дата рождения не может быть в будущем";
            log.debug(message);
            throw new ValidationException(message);
        }
        return user;
    }

}
