package ru.students.tpu.entity;

import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Student {

    private UUID id;

    @ColumnName("last_name")
    private String lastName;

    @ColumnName("first_name")
    private String firstName;

    @ColumnName("middle_name")
    private String middleName;

    @ColumnName("group_id")
    private UUID groupId;

    @ColumnName("date_of_birth")
    private LocalDate dateOfBirth;

    private Gender gender;

    private StudentStatus status;

    public Student() {
        this.id = UUID.randomUUID();
    }
}
