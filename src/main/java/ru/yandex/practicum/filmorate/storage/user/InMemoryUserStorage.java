package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    UserValidation userValidation = new UserValidation();
    public final Map<Integer, User> users = new HashMap<>();
    private int userId = 1;

    @Override
    public User create(User user) {
        userValidation.checkUserCreation(user);
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
    public Set<Integer> addFriend(int id, int friendId) {
        User user = users.get(id);
        User friend = users.get(friendId);
        if (user != null && friend != null) {
            Set<Integer> userFriends = user.getFriends();
            Set<Integer> friendFriends = friend.getFriends();
            userFriends.add(friendId);
            friendFriends.add(id);
            return new HashSet<>(userFriends);
        } else {
            throw new NotFoundException("Пользователь для добавления друга не найден");
        }
    }

    @Override
    public Set<Integer> removeFriend(int id, int friendId) {
        Set<Integer> userFriends = users.get(id).getFriends();
        Set<Integer> friendFriends = users.get(friendId).getFriends();
        if (userFriends != null && friendFriends != null) {
            userFriends.remove(friendId);
            friendFriends.remove(id);
            return new HashSet<>(userFriends);
        } else {
            throw new NotFoundException("Не нашел пользователя для удаления из друзей");
        }
    }

    @Override
    public List<User> getFriendsByUserId(int id) {
        User user = users.get(id);
        if (user != null) {
            Set<Integer> userFriends = user.getFriends();
            if (userFriends != null) {
                List<User> newList = new ArrayList<>();
                for (int i : userFriends) {
                    newList.add(users.get(i));
                }
                return newList;
            } else {
                throw new NotFoundException("Список друзей пуст");
            }
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @Override
    public List<User> findCommonFriends(int id, int otherId) {
        User user = users.get(id);
        User friend = users.get(otherId);
        if (user != null && friend != null) {
            Set<Integer> userFriends = user.getFriends();
            Set<Integer> friendFriends = friend.getFriends();
            if (userFriends != null && friendFriends != null) {
                List<User> newList = new ArrayList<>();
                for (Integer abc : userFriends) {
                    for (Integer efg : friendFriends) {
                        if (abc.equals(efg)) {
                            newList.add(users.get(abc));
                        }
                    }
                }
                return newList;
            } else {
                throw new NotFoundException("Список друзей пуст");
            }
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
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

}
