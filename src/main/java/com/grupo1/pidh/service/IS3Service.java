package com.grupo1.pidh.service;

import org.springframework.web.multipart.MultipartFile;

public interface IS3Service {
    String uploadFile(MultipartFile file);
    void deleteFile(String fileUrl);
    boolean isValidImageFile(MultipartFile file);
}