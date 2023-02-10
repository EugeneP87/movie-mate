package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public Film update(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public Collection<Film> findAll() {
        return inMemoryFilmStorage.findAll();
    }

    public Film getFilmById(int id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public void addLike(int id, int userId) {
        Film film = getFilmById(id);
        Set<Integer> filmLikes = film.getLikes();
        if (getFilmById(id) != null) {
            filmLikes.add(userId);
            film.setLikes(filmLikes);
        } else {
            throw new NotFoundException("Фильм для добавления лайка не найден");
        }
    }

    public void deleteLike(int id, int userId) {
        Film film = getFilmById(id);
        Set<Integer> filmLikes = film.getLikes();
        if (getFilmById(id) != null) {
            if (filmLikes.contains(userId)) {
                filmLikes.remove(userId);
                film.setLikes(filmLikes);
            } else {
                throw new NotFoundException("Лайк пользователя не найден");
            }
        } else {
            throw new NotFoundException("Фильм для удаления лайка не найден");
        }
    }

    public Collection<Film> getPopularFilms(int count) {
        List<Film> filmsList = new ArrayList<>(findAll());
        if (count > 0) {
            return filmsList.stream().filter(film -> !film.getLikes().isEmpty()).collect(Collectors.toList());
        } else {
            return filmsList;
        }
    }

}
