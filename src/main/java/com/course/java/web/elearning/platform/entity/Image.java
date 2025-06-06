package com.course.java.web.elearning.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Base64;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "images")
@Data
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private byte[] image;

  private String mimeType;

  public Image(byte[] image) {
    this.image = image;
  }

  public Image(byte[] image, String mimeType) {
    this.image = image;
    this.mimeType = mimeType;
  }

  public String parseImage() {
    String defaultMimeType = "image/jpeg"; // Default MIME type for existing images
    return image != null ? "data:" + (mimeType != null ? mimeType : defaultMimeType) + ";base64," + Base64.getEncoder().encodeToString(image) : null;
  }
}
