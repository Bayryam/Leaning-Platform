package com.course.java.web.elearning.platform.dto.mapper;

import com.course.java.web.elearning.platform.dto.AnnouncementDto;
import com.course.java.web.elearning.platform.entity.Announcement;

import java.time.LocalDateTime;

public class AnnouncementDtoToAnnouncementMapper {
  public static Announcement mapArticleDtoToArticle(AnnouncementDto announcementDto) {
    final Announcement announcement = new Announcement();
    announcement.setTitle(announcementDto.getTitle());
    announcement.setContent(announcementDto.getContent());
    announcement.setCreatedAt(LocalDateTime.now());
    announcement.setExpiresAt(announcementDto.getExpiresAt());
    return announcement;
  }
}
