package com.example.event_ms.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpyskh5fy",
                "api_key", "732812421118948",
                "api_secret", "vnmnhL63qQnCoxrPXa6RugSjoxY",
                "secure", true
        ));
    }

}
