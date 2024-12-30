package ru.students.tpu.service;

import ru.students.tpu.dto.group.AddGroupRequest;
import ru.students.tpu.dto.group.AddGroupResponse;

public interface GroupService {
    AddGroupResponse addGroup(AddGroupRequest addGroupRequest);
}
