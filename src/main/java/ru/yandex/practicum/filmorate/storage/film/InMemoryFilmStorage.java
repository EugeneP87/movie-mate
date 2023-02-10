package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @Override
    public Film create(Film film) {
        checkFilmCreation(film);
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

    private void checkFilmCreation(Film film) {
        String message;
        if (film.getName().isEmpty()) {
            message = "Название фильма не может быть пустым";
            log.debug(message);
            throw new ValidationException(message);
        }
        int maxDescriptionLength = 200;
        if (film.getDescription().length() > maxDescriptionLength) {
            message = "Максимальная длина описания — 200 символов";
            log.debug(message);
            throw new ValidationException(message);
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            message = "Дата релиза — не раньше 28 декабря 1895 года";
            log.debug(message);
            throw new ValidationException(message);
        }
        if (film.getDuration() <= 0) {
            message = "Продолжительность фильма должна быть положительной";
            log.debug(message);
            throw new ValidationException(message);
        }
    }

}