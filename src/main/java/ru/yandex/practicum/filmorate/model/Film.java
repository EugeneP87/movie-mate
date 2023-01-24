package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    int id;  // целочисленный идентификатор
    @NotBlank
    String name; // название
    String description; // описание
    LocalDate releaseDate; // дата релиза
    int duration; // продолжительность фильма

}
