package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.AnnouncementDto;
import com.course.java.web.elearning.platform.dto.mapper.AnnouncementDtoToAnnouncementMapper;
import com.course.java.web.elearning.platform.entity.Announcement;
import com.course.java.web.elearning.platform.exception.MaximumAnnouncementsException;
import com.course.java.web.elearning.platform.repository.AnnouncementRepository;
import com.course.java.web.elearning.platform.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final int MAX_ANNOUNCEMENTS = 5;

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Announcement addAnnouncement(AnnouncementDto announcement) {
        final List<Announcement> allAnnouncements = announcementRepository.findAll();
        final List<Announcement> activeAnnouncements = allAnnouncements.stream()
                .filter(Announcement::isActive)
                .toList();

        allAnnouncements.removeAll(activeAnnouncements);
        announcementRepository.deleteAll(allAnnouncements);

        if (activeAnnouncements.size() >= MAX_ANNOUNCEMENTS) {
            throw new MaximumAnnouncementsException("Maximum announcements reached! You can add up to 5 announcements!");
        }

        final Announcement announcementToAdd = AnnouncementDtoToAnnouncementMapper.mapArticleDtoToArticle(announcement);
        return announcementRepository.save(announcementToAdd);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    public List<Announcement> getAllActiveAnnouncements() {
        final List<Announcement> allAnnouncements = announcementRepository.findAll();
        return allAnnouncements.stream()
                .filter(Announcement::isActive)
                .toList();
    }
}
