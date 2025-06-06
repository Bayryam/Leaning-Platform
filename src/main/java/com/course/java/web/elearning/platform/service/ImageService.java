package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.ImageDto;
import com.course.java.web.elearning.platform.entity.Image;

public interface ImageService {

  Image getImageById(Long id);
  Image createImage(ImageDto image);
}
