package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private int id;  // целочисленный идентификатор
    private String name; // название
    private String description; // описание
    private LocalDate releaseDate; // дата релиза
    private int duration; // продолжительность фильма
    private Mpa mpa; // рейтинг фильма по MPA
    private List<Genre> genres; // перечень жанров фильма

}

