package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
public class User {

    int id; // целочисленный идентификатор
    @NotBlank
    @Email
    String email; // электронная почта
    @NotBlank
    String login; // логин пользователя
    String name; // имя для отображения
    LocalDate birthday; // дата рождения
}
