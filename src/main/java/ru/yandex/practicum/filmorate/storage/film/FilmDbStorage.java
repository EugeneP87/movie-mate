package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Override
    public Film create(Film film) {
        checkFilmCreation(film);
        String sqlQueryFilm = "INSERT INTO films(film_name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryFilm, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        String sqlQueryGenreAdd = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";
        if (film.getGenres() != null) {
            for (Genre genre : new HashSet<>(film.getGenres())) {
                jdbcTemplate.update(sqlQueryGenreAdd, film.getId(), genre.getId());
            }
        }
        film.setGenres(genreDbStorage.getGenre(film.getId()));
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET film_name = ?, description = ?, release_date = ? , duration = ?, mpa_id = ? " +
                "WHERE id = ?";
        int update = jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId());
        if (update == 0) {
            throw new NotFoundException("Фильм не найден");
        }
        if (film.getGenres() != null) {
            String sqlQueryGenreDelete = "DELETE FROM films_genres WHERE film_id = ?";
            jdbcTemplate.update(sqlQueryGenreDelete, film.getId());
            String sqlQueryGenreAdd = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";
            for (Genre genre : new HashSet<>(film.getGenres())) {
                jdbcTemplate.update(sqlQueryGenreAdd, film.getId(), genre.getId());
            }
        }
        film.setGenres(genreDbStorage.getGenre(film.getId()));
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query("SELECT * FROM films AS f INNER JOIN mpa AS m ON f.mpa_id = m.id", this::mapRowToFilm);
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "SELECT * FROM films WHERE id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (filmRows.next()) {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } else {
            throw new NotFoundException("Фильм не найден");
        }
    }

    public Collection<Film> getPopularFilms(int count) {
        String sqlQuery = "SELECT * FROM films AS f INNER JOIN mpa AS m ON f.mpa_id = m.id " +
                "LEFT OUTER JOIN films_likes AS fl ON f.id = fl.film_id GROUP BY f.id " +
                "ORDER BY COUNT(fl.film_id) DESC";
        if (count > 0) {
            return jdbcTemplate.query(sqlQuery + " LIMIT ?", this::mapRowToFilm, count);
        } else {
            return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
        }
    }

    public Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(mpaDbStorage.getMpaById(resultSet.getInt("mpa_id")))
                .genres(genreDbStorage.getGenre(resultSet.getInt("id")))
                .build();
    }

    public void checkFilmCreation(Film film) {
        String message;
        if (film.getName().isBlank()) {
            message = "Название фильма не может быть пустым";
            log.debug(message);
            throw new IncorrectParameterException(message);
        }
        int maxDescriptionLength = 200;
        if (film.getDescription().length() > maxDescriptionLength) {
            message = "Максимальная длина описания — 200 символов";
            log.debug(message);
            throw new IncorrectParameterException(message);
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            message = "Дата релиза — не раньше 28 декабря 1895 года";
            log.debug(message);
            throw new IncorrectParameterException(message);
        }
        if (film.getDuration() <= 0) {
            message = "Продолжительность фильма должна быть положительной";
            log.debug(message);
            throw new IncorrectParameterException(message);
        }
    }

}