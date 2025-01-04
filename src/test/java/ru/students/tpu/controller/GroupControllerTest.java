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
import ru.students.tpu.dto.group.AddGroupRequest;
import ru.students.tpu.dto.group.AddGroupResponse;
import ru.students.tpu.service.impl.GroupServiceImpl;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты GroupController")
@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    @Mock
    private GroupServiceImpl groupService;

    @InjectMocks
    GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    @DisplayName("Проверка эндпоинта на добавление новой группы")
    void addGroupTest() throws Exception {
        String addGroupRequestJson = """
                {
                    "name": "name",
                    "department": "dapart",
                    "enrollmentYear": "2022"
                }
                """;

        AddGroupResponse addGroupResponse = new AddGroupResponse(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "name",
                "depart",
                2022
        );

        when(groupService.addGroup(any(AddGroupRequest.class))).thenReturn(addGroupResponse);

        mockMvc.perform(post("/api/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addGroupRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addGroupResponse.id().toString()))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.department").value("depart"))
                .andExpect(jsonPath("$.enrollmentYear").value(2022));

        verify(groupService, times(1)).addGroup(any(AddGroupRequest.class));
    }

    @Test
    @DisplayName("Проверка эндпоинта на добавление новой группы с некорректными данными")
    void testAddGroupWithInvalidData() throws Exception {
        String addGroupRequestJson = """
                {
                    "name": "",
                    "department": "depart",
                    "enrollmentYear": 20220
                }
                """;

        mockMvc.perform(post("/api/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addGroupRequestJson))
                .andExpect(status().isBadRequest());
    }
}
