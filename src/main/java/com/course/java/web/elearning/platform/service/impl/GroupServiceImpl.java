package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.GroupDto;
import com.course.java.web.elearning.platform.dto.ImageDto;
import com.course.java.web.elearning.platform.entity.Group;
import com.course.java.web.elearning.platform.entity.Image;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.exception.DuplicatedEntityException;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.GroupRepository;
import com.course.java.web.elearning.platform.repository.UserRepository;
import com.course.java.web.elearning.platform.service.GroupService;
import com.course.java.web.elearning.platform.service.ImageService;
import com.course.java.web.elearning.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserService userService, ImageService imageService, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.imageService = imageService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<Group> getAllGroups() {
        List<Group> allGroups = groupRepository.findAll();
      return allGroups.stream().peek(group -> {
          Image image = group.getImage();
          if (image != null) {
              group.setImageBase64(image.parseImage());
          }
              })
              .toList();
    }

    @Transactional
    @Override
    public Group getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", id), "redirect:/groups"));
        Image image = group.getImage();
        if (image != null) {
            group.setImageBase64(image.parseImage());
        }
        return group;
    }

    @Override
    public Group createGroup(GroupDto groupDto) {
        Group groupForCreate = buildGroup(groupDto);
        if (groupRepository.existsByName(groupForCreate.getName())) {
            throw new DuplicatedEntityException(String.format("Group with name %s already exists", groupForCreate.getName()));
        }
        return groupRepository.save(groupForCreate);
    }

    @Transactional
    @Override
    public Group deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", id), "redirect:/groups"));

        for (User user : group.getMembers()) {
            user.getGroups().remove(group);
        }
        userRepository.saveAll(group.getMembers());

        groupRepository.deleteById(id);
        return group;
    }

    private Group buildGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        groupDto.getMembers().removeAll(List.of(""));
        Set<User> members = groupDto.getMembers().stream().map(userService::getUserByUsername).collect(Collectors.toCollection(HashSet::new));
        group.setMembers(members);
        group.setArticles(groupDto.getArticles());
        group.setDescription(groupDto.getDescription());

        ImageDto imageDto = groupDto.getImage();
        if (imageDto != null) {
            Image savedImage = imageService.createImage(imageDto);
            group.setImage(savedImage);
        }

        return group;
    }

    @Override
    public Group addMember(Long id, String username) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", id), "redirect:/groups/" + id));
        User user = userService.getUserByUsername(username);
        group.addMember(user);
        user.getGroups().add(group);
        userRepository.save(user);
        return groupRepository.save(group);
    }

    @Override
    public Group removeMember(Long id, User user) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %s not found", id), "redirect:/groups/" + id));
        group.removeMember(user);
        user.getGroups().remove(group);
        userRepository.save(user);
        return groupRepository.save(group);
    }
}
