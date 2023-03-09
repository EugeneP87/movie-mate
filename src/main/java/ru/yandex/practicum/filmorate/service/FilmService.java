package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.MpaDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    public final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;
    private final MpaDbStorage mpaDbStorage;

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

    public void addLike(int id, int userId) {
        Film film = getFilmById(id);
        if (film != null && userId > 0) {
            String sqlQuery = "INSERT INTO films_likes (film_id, user_id) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery, id, userId);
        } else {
            throw new NotFoundException("Фильм для добавления лайка не найден");
        }
    }

    public void deleteLike(int id, int userId) {
        Film film = getFilmById(id);
        if (film != null && userId > 0) {
            String sqlQuery = "DELETE FROM films_likes WHERE film_id = ? AND user_id = ?";
            jdbcTemplate.update(sqlQuery, id, userId);
        } else {
            throw new NotFoundException("Фильм для удаления лайка не найден");
        }
    }

    public Collection<Film> getPopularFilms(int count) {
        String sqlQuery = "SELECT * FROM films AS f INNER JOIN mpa AS m ON f.mpa_id = m.id " +
                "LEFT OUTER JOIN films_likes AS fl ON f.id = fl.film_id GROUP BY f.id " +
                "ORDER BY COUNT(fl.user_id) DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }

    public Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(mpaDbStorage.getMpaById(resultSet.getInt("id")))
                .genres(getGenre(resultSet.getInt("id")))
                .build();
    }

    private List<Genre> getGenre(int id) {
        String sqlQuery = "SELECT * FROM genres AS g INNER JOIN films_genres AS fg " +
                "ON g.id = fg.genre_id WHERE fg.film_id = ?";
        return jdbcTemplate.query(sqlQuery, this::composeGenre, id);
    }

    private Genre composeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("genre_name");
        return new Genre(id, name);
    }

}