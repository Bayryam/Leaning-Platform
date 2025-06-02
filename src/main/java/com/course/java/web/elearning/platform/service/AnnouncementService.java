package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.AnnouncementDto;
import com.course.java.web.elearning.platform.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
  List<Announcement> getAllActiveAnnouncements();
  Announcement addAnnouncement(AnnouncementDto announcement);
  void deleteAnnouncement(Long id);
}
