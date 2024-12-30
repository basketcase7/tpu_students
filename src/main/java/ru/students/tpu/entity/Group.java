package ru.students.tpu.entity;

import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

@Data
public class Group {

    private UUID id;

    private String name;

    private String department;

    @ColumnName("enrollment_year")
    private Integer enrollmentYear;

    public Group() {
        this.id = UUID.randomUUID();
    }
}
