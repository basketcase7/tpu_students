package ru.students.tpu.dto.error;

public record FieldError(
        String field,
        String errorMessage
) {
}
