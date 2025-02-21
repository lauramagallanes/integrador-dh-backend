package com.grupo1.pidh.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.grupo1.pidh.exceptions.S3Exception;
import com.grupo1.pidh.service.IS3Service;
import com.grupo1.pidh.utils.images.ImageValidator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service implements IS3Service {
    private final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        if (!isValidImageFile(file)) {
            throw new IllegalArgumentException("Archivo inválido o excede el tamaño permitido");
        }

        try {
            String fileName = generateFileName(file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            LOGGER.error("Error al subir archivo a S3", e);
            throw new S3Exception("Error al subir archivo a S3", e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            String fileName = extractFileNameFromUrl(fileUrl);
            s3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            LOGGER.error("Error al eliminar archivo de S3", e);
            throw new S3Exception("Error al eliminar archivo de S3", e);
        }
    }

    @Override
    public boolean isValidImageFile(MultipartFile file) {
        return ImageValidator.isValid(file);
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "_" +
                StringUtils.cleanPath(file.getOriginalFilename());
    }

    private String extractFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}