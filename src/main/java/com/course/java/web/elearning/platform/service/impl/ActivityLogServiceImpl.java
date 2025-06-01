package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.entity.ActivityLog;
import com.course.java.web.elearning.platform.repository.ActivityLogRepository;
import com.course.java.web.elearning.platform.service.ActivityLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityLogServiceImpl.class);
    private final ActivityLogRepository activityLogRepository;

    @Autowired
    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public List<ActivityLog> findAllLogs() {
        logger.info("Fetching all activity logs");
        return activityLogRepository.findAll(Sort.by(Sort.Order.desc("timestamp")));
    }

    @Override
    public void logActivity(String action, String username) {
        final ActivityLog log = new ActivityLog();
        log.setAction(action);
        log.setUsername(username);
        log.setTimestamp(LocalDateTime.now());
        activityLogRepository.save(log);
    }
}
