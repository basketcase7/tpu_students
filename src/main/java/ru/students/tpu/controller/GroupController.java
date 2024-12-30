package ru.students.tpu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.students.tpu.dto.group.AddGroupRequest;
import ru.students.tpu.dto.group.AddGroupResponse;
import ru.students.tpu.service.GroupService;

@RestController
@RequestMapping("api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping()
    public AddGroupResponse addGroup(@RequestBody @Valid AddGroupRequest addGroupRequest) {
        return groupService.addGroup(addGroupRequest);
    }
}
