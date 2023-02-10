package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.*;

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
        User user = getUserById(id);
        User friend = getUserById(friendId);
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

    public Set<Integer> removeFriend(int id, int friendId) {
        Set<Integer> userFriends = getUserById(id).getFriends();
        Set<Integer> friendFriends = getUserById(friendId).getFriends();
        if (userFriends != null && friendFriends != null) {
            userFriends.remove(friendId);
            friendFriends.remove(id);
            return new HashSet<>(userFriends);
        } else {
            throw new NotFoundException("Не нашел пользователя для удаления из друзей");
        }
    }

    public Collection<User> getFriendsByUserId(int id) {
        User user = getUserById(id);
        if (user != null) {
            Set<Integer> userFriends = user.getFriends();
            if (userFriends != null) {
                List<User> newList = new ArrayList<>();
                for (Integer userFriendsIds : userFriends) {
                    newList.add(getUserById(userFriendsIds));
                }
                return newList;
            } else {
                throw new NotFoundException("Список друзей пуст");
            }
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public List<User> findCommonFriends(int id, int otherId) {
        User user = getUserById(id);
        User friend = getUserById(otherId);
        if (user != null && friend != null) {
            Set<Integer> userFriends = user.getFriends();
            Set<Integer> friendFriends = friend.getFriends();
            if (userFriends != null && friendFriends != null) {
                List<User> newList = new ArrayList<>();
                for (Integer userFriendIds : userFriends) {
                    for (Integer friendFriendIds : friendFriends) {
                        if (userFriendIds.equals(friendFriendIds)) {
                            newList.add(getUserById(userFriendIds));
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

    public User getUserById(int id) {
        return inMemoryUserStorage.getUserById(id);
    }

}
