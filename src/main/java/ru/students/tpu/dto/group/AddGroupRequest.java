package ru.students.tpu.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddGroupRequest(
        @NotBlank(message = "Имя группы не должно быть пустым")
        String name,
        @NotBlank(message = "Подразделение группы не должно быть пустым")
        String department,
        @NotNull(message = "Год набора не должен быть пустым")
        Integer enrollmentYear
) {
}
