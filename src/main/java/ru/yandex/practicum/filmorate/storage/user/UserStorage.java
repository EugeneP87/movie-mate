package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserStorage {

    User create(User user);

    User update(User user);

    Collection<User> findAll();

    Set<Integer> addFriend(int id, int friendId);

    Set<Integer> removeFriend(int id, int friendId);

    Collection<User> getFriendsByUserId(int id);

    List<User> findCommonFriends(int id, int otherId);

    User getUserById(int id);

}
