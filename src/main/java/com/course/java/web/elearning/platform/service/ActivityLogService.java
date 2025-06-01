package com.course.java.web.elearning.platform.service;


import com.course.java.web.elearning.platform.entity.ActivityLog;

import java.util.List;

public interface ActivityLogService {
    List<ActivityLog> findAllLogs();
    void logActivity(String action, String username);
}
