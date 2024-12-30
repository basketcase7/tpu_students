package ru.students.tpu.mapper;

import ru.students.tpu.dto.group.AddGroupRequest;
import ru.students.tpu.dto.group.AddGroupResponse;
import ru.students.tpu.entity.Group;

public class GroupMapper {
    public static Group mapRequestToGroup(AddGroupRequest addGroupRequest) {
        Group group = new Group();
        group.setName(addGroupRequest.name());
        group.setDepartment(addGroupRequest.department());
        group.setEnrollmentYear(addGroupRequest.enrollmentYear());
        return group;
    }

    public static AddGroupResponse mapGroupToResponse(Group group) {
        return new AddGroupResponse(
                group.getId(),
                group.getName(),
                group.getDepartment(),
                group.getEnrollmentYear()
        );
    }
}
