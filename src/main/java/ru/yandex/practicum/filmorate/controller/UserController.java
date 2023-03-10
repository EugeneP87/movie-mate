package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.debug("Создание нового пользователя");
        return userService.create(user);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        log.debug("Обновление данных пользователя");
        return userService.update(user);
    }

    @GetMapping()
    public Collection<User> findAll() {
        log.debug("Получение перечня всех пользователей");
        return userService.findAll();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id,
                          @PathVariable int friendId) {
        log.debug("Добавление друга");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id,
                             @PathVariable int friendId) {
        log.debug("Удаление друга");
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriendsByUserId(@PathVariable int id) {
        log.debug("Возвращение списка пользователей, являющихся его друзьями");
        return userService.getFriendsByUserId(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable int id,
                                        @PathVariable int otherId) {
        log.debug("Список друзей, общих с другим пользователем");
        return userService.findCommonFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.debug("Получение пользователя по ID");
        return userService.getUserById(id);
    }

}