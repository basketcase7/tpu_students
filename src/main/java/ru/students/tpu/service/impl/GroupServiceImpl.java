package ru.students.tpu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.students.tpu.dao.GroupDao;
import ru.students.tpu.dto.group.AddGroupRequest;
import ru.students.tpu.dto.group.AddGroupResponse;
import ru.students.tpu.entity.Group;
import ru.students.tpu.service.GroupService;

import static ru.students.tpu.mapper.GroupMapper.mapGroupToResponse;
import static ru.students.tpu.mapper.GroupMapper.mapRequestToGroup;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;

    @Override
    public AddGroupResponse addGroup(AddGroupRequest addGroupRequest) {
        Group group = mapRequestToGroup(addGroupRequest);
        groupDao.addGroup(group);
        return mapGroupToResponse(group);
    }
}
