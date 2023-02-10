package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    public final Map<Integer, User> users = new HashMap<>();
    private int userId = 1;

    @Override
    public User create(User user) {
        checkUserCreation(user);
        user.setId(userId);
        users.put(userId, user);
        userId++;
        return user;
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Пользователь для обновления данных не найден");
        }
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User getUserById(int id) {
        User user = users.get(id);
        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("Пользователь для отображения не найден");
        }
    }

    private void checkUserCreation(User user) {
        String message;
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
    }

}