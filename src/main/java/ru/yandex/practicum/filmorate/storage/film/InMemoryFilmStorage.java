package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    FilmValidation filmValidation = new FilmValidation();
    private final Map<Integer, Film> films = new HashMap<>();
    Set<Integer> likes = new HashSet<>();
    private int filmId = 1;

    @Override
    public Film create(Film film) {
        filmValidation.checkFilmCreation(film);
        film.setId(filmId);
        films.put(filmId, film);
        filmId++;
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Фильм для обновления данных не найден");
        }
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film getFilmById(int id) {
        Film film = films.get(id);
        if (film != null) {
            return film;
        } else {
            throw new NotFoundException("Фильм для отображения не найден");
        }
    }

    @Override
    public void addLike(int id, int userId) {
        Film film = films.get(id);
        if (film != null) {
            likes.add(userId);
            film.setLikes(likes);
        } else {
            throw new NotFoundException("Фильм для добавления лайка не найден");
        }
    }

    @Override
    public void deleteLike(int id, int userId) {
        Film film = films.get(id);
        if (film != null) {
            if (likes.contains(userId)) {
                likes.remove(userId);
                film.setLikes(likes);
            } else {
                throw new NotFoundException("Лайк пользователя не найден");
            }
        } else {
            throw new NotFoundException("Фильм для удаления лайка не найден");
        }
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        List<Film> filmsList = new ArrayList<>(films.values());
        if (count > 0) {
            return filmsList.stream().filter(film -> film.getLikes() != null).collect(Collectors.toList());
        } else {
            return filmsList;
        }
    }

}