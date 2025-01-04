package ru.students.tpu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.students.tpu.dto.student.AddStudentRequest;
import ru.students.tpu.dto.student.SearchStudentsFilter;
import ru.students.tpu.dto.student.StudentInfo;
import ru.students.tpu.service.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping()
    public StudentInfo addStudent(@RequestBody @Valid AddStudentRequest addStudentRequest) {
        return studentService.addStudent(addStudentRequest);
    }

    @GetMapping("/{id}")
    public StudentInfo getStudent(@PathVariable UUID id) {
        return studentService.getStudent(id);
    }

    @GetMapping
    public List<StudentInfo> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/group/{groupId}")
    public List<StudentInfo> getStudentsByGroup(@PathVariable UUID groupId) {
        return studentService.getAllByGroup(groupId);
    }

    @GetMapping("/search")
    public List<StudentInfo> searchStudentsByParams(@ModelAttribute @Valid SearchStudentsFilter searchStudentsFilter) {
        return studentService.searchStudentsByParams(searchStudentsFilter);
    }
}
