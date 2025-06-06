package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.GroupDto;
import com.course.java.web.elearning.platform.entity.Group;
import com.course.java.web.elearning.platform.entity.User;

import java.util.List;

public interface GroupService {
  List<Group> getAllGroups();
  Group getGroupById(Long id);
  Group createGroup(GroupDto groupDto);
  Group deleteGroup(Long id);
  Group addMember(Long id, String username);
  Group removeMember(Long id, User user);
}
