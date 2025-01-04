package ru.students.tpu.service;

import ru.students.tpu.dto.student.AddStudentRequest;
import ru.students.tpu.dto.student.SearchStudentsFilter;
import ru.students.tpu.dto.student.StudentInfo;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentInfo addStudent(AddStudentRequest addStudentRequest);
    StudentInfo getStudent(UUID id);
    List<StudentInfo> getAllStudents();
    List<StudentInfo> getAllByGroup(UUID groupId);
    List<StudentInfo> searchStudentsByParams(SearchStudentsFilter searchStudentsFilter);
}
