package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.ImageDto;
import com.course.java.web.elearning.platform.entity.Image;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.ImageRepository;
import com.course.java.web.elearning.platform.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;

  @Autowired
  public ImageServiceImpl(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public Image getImageById(Long id) {
    return imageRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Image with id %s not found", id), "redirect:/groups"));
  }

  @Override
  public Image createImage(ImageDto imageDto) {
    Image imageToBeSaved = new Image(imageDto.getImageBytes(), imageDto.getMimeType());
    return imageRepository.save(imageToBeSaved);
  }
}
