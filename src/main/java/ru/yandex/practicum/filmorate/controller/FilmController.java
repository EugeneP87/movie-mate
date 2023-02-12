package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RequestMapping("/films")
@RestController
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Создание нового фильма");
        return filmService.create(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Обновление данных фильма");
        return filmService.update(film);
    }

    @GetMapping()
    public Collection<Film> findAll() {
        log.debug("Получение перечня всех фильмов");
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.debug("Получение фильма с ID: " + id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id,
                        @PathVariable int userId) {
        log.debug("Добавление лайка фильму с ID: " + id + " от пользователя с ID: " + userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id,
                           @PathVariable int userId) {
        log.debug("Удаление лайка у фильма с ID: " + id + " от пользователя с ID: " + userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(value = "count", defaultValue = "0", required = false) Integer count) {
        log.debug("Возвращение списка популярных фильмов");
        return filmService.getPopularFilms(count);
    }

}