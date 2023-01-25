package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    FilmValidation filmValidation = new FilmValidation();
    private int filmId = 1;

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Создание нового фильма");
        filmValidation.checkFilmCreation(film);
        film.setId(filmId);
        films.put(filmId, film);
        filmId++;
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Обновление данных фильма");
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Фильм для обновления данных не найден");
        }
        return film;
    }

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.debug("Получение перечня всех фильмов");
        return films.values();
    }

}
