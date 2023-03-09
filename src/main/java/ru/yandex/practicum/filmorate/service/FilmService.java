package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {

    public final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;

    public Film create(Film film) {
        return filmDbStorage.create(film);
    }

    public Film update(Film film) {
        return filmDbStorage.update(film);
    }

    public Collection<Film> findAll() {
        return filmDbStorage.findAll();
    }

    public Film getFilmById(int id) {
        return filmDbStorage.getFilmById(id);
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmDbStorage.getPopularFilms(count);
    }

    public void addLike(int id, int userId) {
        if (userId > 0) {
            String sqlQuery = "INSERT INTO films_likes (film_id, user_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery, id, userId);
        } else {
            throw new NotFoundException("Невозможно добавить лайк");
        }
    }

    public void deleteLike(int id, int userId) {
        if (userId > 0) {
            String sqlQuery = "DELETE FROM films_likes WHERE film_id = ? AND user_id = ?";
            jdbcTemplate.update(sqlQuery, id, userId);
        } else {
            throw new NotFoundException("Невозможно удалить лайк");
        }
    }

}