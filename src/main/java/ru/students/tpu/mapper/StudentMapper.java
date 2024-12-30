package ru.students.tpu.mapper;

import ru.students.tpu.dto.student.AddStudentRequest;
import ru.students.tpu.dto.student.StudentInfo;
import ru.students.tpu.entity.Student;

import java.time.LocalDate;

public class StudentMapper {
    public static Student mapRequestToStudent(AddStudentRequest addStudentRequest) {
        Student student = new Student();
        student.setFirstName(addStudentRequest.firstName());
        student.setLastName(addStudentRequest.lastName());
        student.setMiddleName(addStudentRequest.middleName());
        student.setDateOfBirth(LocalDate.parse(addStudentRequest.dateOfBirth()));
        student.setGroupId(addStudentRequest.groupId());
        student.setGender(addStudentRequest.gender());
        student.setStatus(addStudentRequest.status());
        return student;
    }

    public static StudentInfo mapStudentToInfo(Student student) {
        return new StudentInfo(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getMiddleName(),
                student.getGroupId(),
                student.getDateOfBirth().toString(),
                student.getGender(),
                student.getStatus()
        );
    }
}
