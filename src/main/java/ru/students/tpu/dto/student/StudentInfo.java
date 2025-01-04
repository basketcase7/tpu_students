package ru.students.tpu.dto.student;

import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.util.UUID;

public record StudentInfo(
        UUID id,
        String lastName,
        String firstName,
        String middleName,
        UUID groupId,
        String dateOfBirth,
        Gender gender,
        StudentStatus status
) {
}
