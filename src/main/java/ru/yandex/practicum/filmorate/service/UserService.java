package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;

    public User create(User user) {
        return userDbStorage.create(user);
    }

    public User update(User user) {
        return userDbStorage.update(user);
    }

    public Collection<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", this::mapRowToUser);
    }

    public void addFriend(int id, int friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        if (user != null && friend != null) {
            String sqlQuery = "INSERT INTO friends(user_id, friend_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
        } else {
            throw new NotFoundException("Пользователь для добавления друга не найден");
        }
    }

    public void removeFriend(int id, int friendId) {
        User user = getUserById(id);
        User friend = getUserById(friendId);
        if (user != null && friend != null) {
            String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
        } else {
            throw new NotFoundException("Не нашел пользователя для удаления из друзей");
        }
    }

    public Collection<User> getFriendsByUserId(int id) {
        User user = getUserById(id);
        if (user != null) {
            return jdbcTemplate.query("SELECT * FROM users WHERE id " +
                    "IN (SELECT friend_id FROM friends WHERE user_id = ?)", this::mapRowToUser, id);
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public List<User> findCommonFriends(int id, int otherId) {
        User user = getUserById(id);
        User friend = getUserById(otherId);
        if (user != null && friend != null) {
            String sqlQuery = "SELECT * FROM users WHERE id IN (SELECT f1.friend_id FROM friends AS f1 " +
                    "INNER JOIN friends AS f2 ON f1.friend_id = f2.friend_id AND f1.user_id = ? WHERE f2.user_id = ?)";
            return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id, otherId);
        } else {
            throw new NotFoundException("Список друзей пуст или пользователь не найден");
        }
    }

    public User getUserById(int id) {
        return userDbStorage.getUserById(id);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("user_name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

}