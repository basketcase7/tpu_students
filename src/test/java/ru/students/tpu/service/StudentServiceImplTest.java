package ru.students.tpu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.students.tpu.dao.GroupDao;
import ru.students.tpu.dao.StudentDao;
import ru.students.tpu.dto.student.SearchStudentsFilter;
import ru.students.tpu.dto.student.StudentInfo;
import ru.students.tpu.entity.Group;
import ru.students.tpu.entity.Student;
import ru.students.tpu.exception.EntityNotFoundException;
import ru.students.tpu.service.impl.StudentServiceImpl;
import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тесты StudentServiceImpl")
@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    StudentServiceImpl studentService;

    @Mock
    StudentDao studentDao;

    @Mock
    GroupDao groupDao;

    Student firstStudent;
    Student secondStudent;
    StudentInfo firstStudentInfo;
    StudentInfo secondStudentInfo;

    @BeforeEach
    void setUp() {
        firstStudent = new Student();
        firstStudent.setId(UUID.randomUUID());
        firstStudent.setFirstName("First");
        firstStudent.setLastName("Last");
        firstStudent.setMiddleName("Middle");
        firstStudent.setStatus(StudentStatus.STUDYING);
        firstStudent.setGender(Gender.MALE);
        firstStudent.setGroupId(UUID.randomUUID());
        firstStudent.setDateOfBirth(LocalDate.parse("2004-05-05"));

        secondStudent = new Student();
        secondStudent.setId(UUID.randomUUID());
        secondStudent.setFirstName("Firr");
        secondStudent.setLastName("Lass");
        secondStudent.setMiddleName("Midd");
        secondStudent.setStatus(StudentStatus.STUDYING);
        secondStudent.setGender(Gender.FEMALE);
        secondStudent.setGroupId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        secondStudent.setDateOfBirth(LocalDate.parse("2005-05-05"));

        firstStudentInfo = new StudentInfo(
                firstStudent.getId(),
                firstStudent.getFirstName(),
                firstStudent.getLastName(),
                firstStudent.getMiddleName(),
                firstStudent.getGroupId(),
                firstStudent.getDateOfBirth().toString(),
                firstStudent.getGender(),
                firstStudent.getStatus()
        );

        secondStudentInfo = new StudentInfo(
                secondStudent.getId(),
                secondStudent.getFirstName(),
                secondStudent.getLastName(),
                secondStudent.getMiddleName(),
                secondStudent.getGroupId(),
                secondStudent.getDateOfBirth().toString(),
                secondStudent.getGender(),
                secondStudent.getStatus()
        );
    }

    @Test
    @DisplayName("Проверка метода поиска студента по айди, когда студент существует")
    void testGetStudentWithRealStudent() {
        UUID studentId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        Student student = new Student();
        student.setDateOfBirth(LocalDate.parse("2004-05-05"));

        when(studentDao.getStudentById(studentId)).thenReturn(Optional.of(student));

        studentService.getStudent(studentId);

        verify(studentDao).getStudentById(studentId);
    }

    @Test
    @DisplayName("Проверка метода поиска студента по айди, когда студента нет")
    void testGetStudentWithFakeStudent() {
        UUID studentId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudent(studentId));
    }

    @Test
    @DisplayName("Проверка метода поиска всех студентов когда студенты есть")
    void testGetAllStudentsWithStudents() {
        when(studentDao.getAllStudents()).thenReturn(List.of(firstStudent, secondStudent));

        List<StudentInfo> actualStudents = studentService.getAllStudents();
        List<StudentInfo> expectedStudents = List.of(firstStudentInfo, secondStudentInfo);

        assertEquals(actualStudents, expectedStudents);
    }

    @Test
    @DisplayName("Проверка метода поиска всех студентов когда, студентов нет")
    void testGetAllStudentsWithoutStudents() {
        assertThrows(EntityNotFoundException.class, () -> studentService.getAllStudents());
    }

    @Test
    @DisplayName("Проверка метода поиска всех студентов по группе, когда группы не существует")
    void testAllByGroupWithoutGroup() {
        UUID groupId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        assertThrows(EntityNotFoundException.class, () -> studentService.getAllByGroup(groupId));
    }

    @Test
    @DisplayName("Проверка метода поиска всех студентов по группе")
    void testAllByGroupWithGroup() {
        Group group = new Group();
        group.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        group.setName("name");
        group.setDepartment("depart");
        group.setEnrollmentYear(2022);

        List<Student> expectedStudents = List.of(firstStudent);

        List<StudentInfo> expectedStudentsInfo = List.of(firstStudentInfo);

        when(groupDao.findByGroupId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")))
                .thenReturn(Optional.of(group));
        when(studentDao.getStudentsByGroup(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")))
                .thenReturn(expectedStudents);

        List<StudentInfo> actualStudentsInfo = studentService.
                getAllByGroup(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        assertEquals(actualStudentsInfo, expectedStudentsInfo);
    }

    @Test
    @DisplayName("Проверка метода поиска студента по параметрам, когда стундентов нет")
    void testSearchStudentsByParamsWithoutStudents() {
        SearchStudentsFilter searchStudentsFilter = new SearchStudentsFilter(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertThrows(EntityNotFoundException.class, () -> studentService.searchStudentsByParams(searchStudentsFilter));
    }

    @Test
    @DisplayName("Проверка метода поиска студента по параметрам")
    void testSearchStudentsByParams() {
        SearchStudentsFilter searchStudentsFilter = new SearchStudentsFilter(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Gender.FEMALE,
                StudentStatus.STUDYING,
                null
        );

        when(studentDao.searchStudents(
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
        )).thenReturn(List.of(secondStudent));

        List<StudentInfo> actualStudentsInfo = studentService.searchStudentsByParams(searchStudentsFilter);
        List<StudentInfo> expectedStudentsInfo = List.of(secondStudentInfo);

        assertEquals(actualStudentsInfo, expectedStudentsInfo);
    }
}
