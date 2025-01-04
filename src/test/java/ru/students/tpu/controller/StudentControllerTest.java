package ru.students.tpu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.students.tpu.dto.student.AddStudentRequest;
import ru.students.tpu.dto.student.SearchStudentsFilter;
import ru.students.tpu.dto.student.StudentInfo;
import ru.students.tpu.service.impl.StudentServiceImpl;
import ru.students.tpu.type.Gender;
import ru.students.tpu.type.StudentStatus;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты StudentController")
@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    @DisplayName("Проверка эндпоинта на добавление нового студента")
    void testAddStudent() throws Exception {
        String addStudentRequestJson = """
                {
                    "lastName": "Last",
                    "firstName": "First",
                    "middleName": "Middle",
                    "groupId": "123e4567-e89b-12d3-a456-426614174001",
                    "dateOfBirth": "2004-05-05",
                    "gender": "MALE",
                    "status": "STUDYING"
                }
                """;

        StudentInfo studentInfo = new StudentInfo(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "Last",
                "First",
                "Middle",
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "2004-05-05",
                Gender.MALE,
                StudentStatus.STUDYING
        );

        when(studentService.addStudent(any(AddStudentRequest.class))).thenReturn(studentInfo);

        mockMvc.perform(post("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStudentRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentInfo.id().toString()))
                .andExpect(jsonPath("$.lastName").value("Last"))
                .andExpect(jsonPath("$.firstName").value("First"))
                .andExpect(jsonPath("$.middleName").value("Middle"))
                .andExpect(jsonPath("$.groupId").value(studentInfo.groupId().toString()))
                .andExpect(jsonPath("$.dateOfBirth").value("2004-05-05"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.status").value("STUDYING"));

        verify(studentService, times(1)).addStudent(any(AddStudentRequest.class));
    }

    @Test
    @DisplayName("Проверка эндпоинта на добавление нового студента с некорректными данными")
    void testAddStudentWithInvalidData() throws Exception {
        String addStudentRequestJson = """
                {
                    "lastName": "",
                    "firstName": "",
                    "middleName": "Middle",
                    "groupId": "123e4567-e89b-12d3-a456-426614174001",
                    "dateOfBirth": "20054-05-05",
                    "gender": "MALE",
                    "status": "STUDYING"
                }
                """;

        mockMvc.perform(post("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStudentRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Проверка эндпоинта на поиск студента по айди")
    void testGetStudent() throws Exception {
        StudentInfo student = new StudentInfo(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "Last",
                "First",
                "Middle",
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "2002-05-05",
                Gender.MALE,
                StudentStatus.STUDYING
        );

        when(studentService.getStudent(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))).thenReturn(student);

        mockMvc.perform(get("/api/student/{id}",
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174000")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(UUID.fromString("123e4567-e89b-12d3-a456-426614174000").toString()))
                .andExpect(jsonPath("$.firstName")
                        .value("First"))
                .andExpect(jsonPath("$.lastName")
                        .value("Last"));
        verify(studentService, times(1))
                .getStudent(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    @DisplayName("Проверка эндпоинта на получение всех студентов")
    void testGetAllStudents() throws Exception {
        List<StudentInfo> students = List.of(
                new StudentInfo(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                        "Last1", "First1", "Middle1",
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                        "2002-05-05", Gender.MALE, StudentStatus.STUDYING
                ),
                new StudentInfo(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                        "Last2", "First2", "Middle2",
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174003"),
                        "2003-06-06", Gender.FEMALE, StudentStatus.EXPELLED
                )
        );

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id")
                        .value(UUID.fromString("123e4567-e89b-12d3-a456-426614174000").toString()))
                .andExpect(jsonPath("$[0].firstName")
                        .value("First1"))
                .andExpect(jsonPath("$[1].id")
                        .value(UUID.fromString("123e4567-e89b-12d3-a456-426614174002").toString()))
                .andExpect(jsonPath("$[1].firstName")
                        .value("First2"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    @DisplayName("Проверка эндпоинта на полученние всех студентов из одной группы")
    void testGetStudentsByGroup() throws Exception {
        UUID groupId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        List<StudentInfo> students = List.of(
                new StudentInfo(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                        "Last", "First", "Middle",
                        groupId,
                        "2002-05-05", Gender.MALE, StudentStatus.STUDYING
                )
        );

        when(studentService.getAllByGroup(groupId)).thenReturn(students);

        mockMvc.perform(get("/api/student/group/{groupId}", groupId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupId").value(groupId.toString()))
                .andExpect(jsonPath("$[0].firstName").value("First"));

        verify(studentService, times(1)).getAllByGroup(groupId);
    }

    @Test
    @DisplayName("Проверка эндпоинта получение студентов по фильтру")
    void testSearchStudentsByParams() throws Exception {
        SearchStudentsFilter filter = new SearchStudentsFilter(
                null,
                null,
                null,
                null,
                null,
                "2004-05-05",
                "2005-07-07",
                null,
                StudentStatus.STUDYING,
                null);

        List<StudentInfo> students = List.of(
                new StudentInfo(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                        "Last", "First", "Middle",
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                        "2004-07-07", Gender.MALE, StudentStatus.STUDYING
                )
        );

        when(studentService.searchStudentsByParams(filter)).thenReturn(students);

        mockMvc.perform(get("/api/student/search")
                        .param("startBirth", "2004-05-05")
                        .param("endBirth", "2005-07-07")
                        .param("status", "STUDYING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("First"))
                .andExpect(jsonPath("$[0].lastName").value("Last"));

        verify(studentService, times(1)).searchStudentsByParams(filter);
    }

    @Test
    @DisplayName("Проверка эндпоинта получение студентов по фильтру с некорректными данными")
    void testSearchStudentsByParamsWithInvalidData() throws Exception {
        mockMvc.perform(get("/api/student/search")
                        .param("startBirth", "20204-05-05")
                        .param("endBirth", "20205-07-07")
                        .param("status", "STUDYING"))
                .andExpect(status().isBadRequest());
    }

}
