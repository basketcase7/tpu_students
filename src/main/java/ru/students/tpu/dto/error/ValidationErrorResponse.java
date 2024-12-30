package ru.students.tpu.dto.error;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
        int status,
        String message,
        LocalDateTime timeStamp,
        List<FieldError> fieldErrors
) {
}
