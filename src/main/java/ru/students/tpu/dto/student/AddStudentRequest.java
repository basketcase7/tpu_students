package ru.students.tpu.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.util.UUID;

public record AddStudentRequest(
        @NotBlank(message = "Имя студента не должно быть пустым")
        @Size(max = 50, message = "Имя студента должно содержать до 50 символов")
        String lastName,

        @NotBlank(message = "Фамилия студента не должна быть пустой")
        @Size(max = 50, message = "Фамилия студента должна содержать до 50 символов")
        String firstName,

        @Size(max = 50, message = "Отчество студента не должно содержать более 50 символов")
        String middleName,

        @NotNull(message = "Группа студента не должна быть пустой")
        UUID groupId,

        @NotBlank(message = "Дата рождения студента не должна быть пустой")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата рождения должна быть в формате YYYY-MM-DD")
        String dateOfBirth,

        @NotNull(message = "Пол студента не должен быть пустым")
        Gender gender,

        @NotNull(message = "Статус студента не должен быть пустым")
        StudentStatus status
) {
}
