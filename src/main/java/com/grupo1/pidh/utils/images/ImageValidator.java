package com.grupo1.pidh.utils.images;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidator {
    private static final List<String> VALID_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "gif", "webp");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public static boolean isValid(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return false;
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return VALID_EXTENSIONS.contains(extension);
    }
}