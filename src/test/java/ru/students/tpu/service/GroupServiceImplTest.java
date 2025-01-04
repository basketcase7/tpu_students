package ru.students.tpu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.students.tpu.dao.GroupDao;
import ru.students.tpu.dto.group.AddGroupRequest;
import ru.students.tpu.entity.Group;
import ru.students.tpu.service.impl.GroupServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тесты GroupServiceImpl")
@ExtendWith(MockitoExtension.class)
public class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupDao groupDao;

    @DisplayName("Проверка метода testAddGroup")
    @Test
    void testAddGroup() {

        AddGroupRequest addGroupRequest = new AddGroupRequest("group", "depart", 2022);

        groupService.addGroup(addGroupRequest);

        ArgumentCaptor<Group> captor = ArgumentCaptor.forClass(Group.class);
        verify(groupDao, times(1)).addGroup(captor.capture());

        Group capturedGroup = captor.getValue();
        assertEquals("group", capturedGroup.getName());
        assertEquals("depart", capturedGroup.getDepartment());
        assertEquals(2022, capturedGroup.getEnrollmentYear());
    }

}
