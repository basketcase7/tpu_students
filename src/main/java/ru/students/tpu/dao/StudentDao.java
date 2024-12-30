package ru.students.tpu.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.students.tpu.entity.Student;
import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(Student.class)
public interface StudentDao {
    @SqlUpdate("""
            insert into students (id, last_name, first_name, middle_name, group_id, date_of_birth, gender, status)
            values (:id, :lastName, :firstName, :middleName, :groupId, :dateOfBirth, :gender, :status)
            """)
    @GetGeneratedKeys
    UUID addStudent(@BindBean Student student);

    @SqlQuery("""
            select * from students where id = :id
            """)
    Optional<Student> getStudentById(UUID id);

    @SqlQuery("""
            select * from students
            """)
    List<Student> getAllStudents();

    @SqlQuery("""
            select * from students
            where group_id = :groupId
            order by last_name, first_name, middle_name
            """)
    List<Student> getStudentsByGroup(UUID groupId);

    @SqlQuery("""
            select s.id as student_id, s.first_name, s.last_name, s.middle_name, g.id as group_id,
            s.date_of_birth, s.gender, s.status
            from students s
            join groups g on s.group_id = g.id
            where (:id is null or s.id = cast(:id as uuid))
            and (:lastName is null or s.last_name ilike :lastName)
            and (:firstName is null or s.first_name ilike :firstName)
            and (:middleName is null or s.middle_name ilike :middleName)
            and (:groupName is null or g.name = :groupName)
            and (:startBirth is null or s.date_of_birth >= cast(:startBirth AS DATE))
            and (:endBirth is null or s.date_of_birth <= cast(:endBirth AS DATE))
            and (:gender is null or s.gender ilike :gender)
            and (:status is null or s.status ilike :status)
            and (:yearOfStudying is null or (extract(year from current_date) - g.enrollment_year + 1) = :yearOfStudying)
            order by s.last_name, s.first_name, s.middle_name
            """)
    List<Student> searchStudents(
            UUID id,
            String lastName,
            String firstName,
            String middleName,
            String groupName,
            String startBirth,
            String endBirth,
            Gender gender,
            StudentStatus status,
            Integer yearOfStudying
    );
}
