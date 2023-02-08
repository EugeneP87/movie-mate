package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    int id; // целочисленный идентификатор
    @NotBlank
    @Email
    String email; // электронная почта
    @NotBlank
    String login; // логин пользователя
    String name; // имя для отображения
    LocalDate birthday; // дата рождения
    Set<Integer> friends = new HashSet<>(); // список друзей

}
