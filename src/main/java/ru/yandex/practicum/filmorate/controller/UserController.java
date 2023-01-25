package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    UserValidation userValidation = new UserValidation();

    private int userId = 1;

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.debug("Создание нового пользователя");
        userValidation.checkUserCreation(user);
        user.setId(userId);
        users.put(userId, user);
        userId++;
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        log.debug("Обновление данных пользователя");
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Пользователь для обновления данных не найден");
        }
        return user;
    }

    @GetMapping("/users")
    public Collection<User> findAll() {
        log.debug("Получение перечня всех пользователей");
        return users.values();
    }

}
