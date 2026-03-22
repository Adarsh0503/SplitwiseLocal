package com.splitWise.demo.controller;

import com.splitWise.demo.exception.ResourceNotFoundException;
import com.splitWise.demo.model.Group;
import com.splitWise.demo.repository.GroupRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group createGroup(@Valid @RequestBody Group group) {
        return groupRepository.save(group);
    }

    @GetMapping("/{groupId}")
    public Group getGroup(@PathVariable Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group not found with id: " + groupId));
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
