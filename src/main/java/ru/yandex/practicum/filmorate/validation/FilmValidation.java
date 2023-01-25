package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidation {

    String message;

    public Film checkFilmCreation(Film film) {
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
        return film;
    }

}
