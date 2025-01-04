package ru.students.tpu.dto.student;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.util.UUID;

public record SearchStudentsFilter(
        UUID id,

        @Size(max = 50, message = "Фамилия студента должна содержать до 50 символов")
        String lastName,

        @Size(max = 50, message = "Имя студента должно содержать до 50 символов")
        String firstName,

        @Size(max = 50, message = "Отчество студента не должно содержать более 50 символов")
        String middleName,

        String groupName,

        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата рождения должна быть в формате YYYY-MM-DD")
        String startBirth,

        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата рождения должна быть в формате YYYY-MM-DD")
        String endBirth,

        Gender gender,

        StudentStatus status,

        @Min(value = 1, message = "Год обучения должен быть не меньше 1")
        @Max(value = 10, message = "Год обучения должен быть не больше 10")
        Integer yearOfStudying
) {
}
