package com.apex.tech3.wallt_app.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class CloudinaryUploadService {
    private Cloudinary cloudinary;

    public CloudinaryUploadService(Environment env) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", env.getProperty("cdn.cloud.name"),
                "api_key", env.getProperty("cdn.api.key"),
                "api_secret", env.getProperty("cdn.api.secret")));
    }

    public String uploadImage(File file) {
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (String) uploadResult.get("url");
    }
}

