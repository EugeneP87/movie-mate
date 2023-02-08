package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) {
        return inMemoryUserStorage.update(user);
    }

    public Collection<User> findAll() {
        return inMemoryUserStorage.findAll();
    }

    public Set<Integer> addFriend(int id, int friendId) {
        return inMemoryUserStorage.addFriend(id, friendId);
    }

    public Set<Integer> removeFriend(int id, int friendId) {
        return inMemoryUserStorage.removeFriend(id, friendId);
    }

    public Collection<User> getFriendsByUserId(int id) {
        return inMemoryUserStorage.getFriendsByUserId(id);
    }

    public List<User> findCommonFriends(int id, int otherId) {
        return inMemoryUserStorage.findCommonFriends(id, otherId);
    }

    public User getUserById(int id) {
        return inMemoryUserStorage.getUserById(id);
    }

}
