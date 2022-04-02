package com.phoenix.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CloudinaryServiceImplTest {

    @Autowired
    @Qualifier("cloudinary-service")
    CloudinaryService cloudinaryService;


    @Autowired
    Cloudinary cloudinary;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Cloudinary object instantiated test")
    void cloudinaryObjectInstanceTest(){
    assertThat(cloudinary).isNotNull();
    }


    @Test
    void uploadToCloudinaryTest() throws IOException {
            Path file = Paths.get("src/test/resources/best-coding-fonts-blog-hero-1200x630.jpg");
            assertThat(file.toFile().exists()).isTrue();
            Map<?, ? > uploadResult = cloudinaryService.upload(Files.readAllBytes(file), ObjectUtils.emptyMap());
            log.info("upload result to cloud -> {}", uploadResult);
            assertThat(uploadResult.get("url")).isNotNull();

    }

    @Test
    void uploadMultipartToCloudinaryTest() throws IOException{

        Path path = Paths.get("src/test/resources/best-coding-fonts-blog-hero-1200x630.jpg");

        assertThat(path.toFile().exists());
        assertThat(path.getFileName().toString()).isEqualTo("best-coding-fonts-blog-hero-1200x630.jpg");

        MultipartFile multipartFile = new  MockMultipartFile(path.getFileName().toString(), path.getFileName().toString(), "img/jpg", Files.readAllBytes(path));

        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();


      Map<?, ?> getUpload = cloudinaryService.upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
              ObjectUtils.asMap(
                      "overwrite", true
              );
      assertThat(getUpload).isNotNull();
    }
}