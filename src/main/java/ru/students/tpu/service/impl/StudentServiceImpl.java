package ru.students.tpu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.students.tpu.dao.GroupDao;
import ru.students.tpu.dao.StudentDao;
import ru.students.tpu.dto.student.AddStudentRequest;
import ru.students.tpu.dto.student.SearchStudentsFilter;
import ru.students.tpu.dto.student.StudentInfo;
import ru.students.tpu.entity.Student;
import ru.students.tpu.exception.EntityNotFoundException;
import ru.students.tpu.mapper.StudentMapper;
import ru.students.tpu.service.StudentService;

import java.util.List;
import java.util.UUID;

import static ru.students.tpu.mapper.StudentMapper.mapRequestToStudent;
import static ru.students.tpu.mapper.StudentMapper.mapStudentToInfo;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final GroupDao groupDao;

    @Override
    public StudentInfo addStudent(AddStudentRequest addStudentRequest) {
        groupDao.findByGroupId(addStudentRequest.groupId())
                .orElseThrow(() -> new EntityNotFoundException("Группа не найдена"));

        Student student = mapRequestToStudent(addStudentRequest);
        studentDao.addStudent(student);
        return mapStudentToInfo(student);
    }

    @Override
    public StudentInfo getStudent(UUID id) {
        Student student = studentDao.getStudentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден"));

        return mapStudentToInfo(student);
    }

    @Override
    public List<StudentInfo> getAllStudents() {
        List<Student> students = studentDao.getAllStudents();

        if (students.isEmpty()) {
            throw new EntityNotFoundException("Студенты не найдены");
        }

        return students
                .stream()
                .map(StudentMapper::mapStudentToInfo)
                .toList();
    }

    @Override
    public List<StudentInfo> getAllByGroup(UUID groupId) {
        groupDao.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Группа не найдена"));

        List<Student> students = studentDao.getStudentsByGroup(groupId);

        if (students.isEmpty()) {
            throw new EntityNotFoundException("Студенты не найдены");
        }

        return students.stream()
                .map(StudentMapper::mapStudentToInfo)
                .toList();
    }

    @Override
    public List<StudentInfo> searchStudentsByParams(SearchStudentsFilter searchStudentsFilter) {

        List<Student> students = studentDao.searchStudents(
                searchStudentsFilter.id(),
                searchStudentsFilter.lastName(),
                searchStudentsFilter.firstName(),
                searchStudentsFilter.middleName(),
                searchStudentsFilter.groupName(),
                searchStudentsFilter.startBirth(),
                searchStudentsFilter.endBirth(),
                searchStudentsFilter.gender(),
                searchStudentsFilter.status(),
                searchStudentsFilter.yearOfStudying()
        );

        if (students.isEmpty()) {
            throw new EntityNotFoundException("Студенты не найдены");
        }

        return students.stream()
                .map(StudentMapper::mapStudentToInfo)
                .toList();
    }
}
