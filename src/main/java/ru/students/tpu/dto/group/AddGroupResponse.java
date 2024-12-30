package ru.students.tpu.dto.group;

import java.util.UUID;

public record AddGroupResponse(
        UUID id,
        String name,
        String department,
        Integer enrollmentYear
) {
}
