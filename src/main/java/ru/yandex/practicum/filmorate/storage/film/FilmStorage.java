package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Collection<Film> findAll();

    Film getFilmById(int id);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);

    Collection<Film> getPopularFilms(int count);

}
