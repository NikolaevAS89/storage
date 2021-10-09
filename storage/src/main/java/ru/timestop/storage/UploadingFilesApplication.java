package ru.timestop.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import ru.timestop.storage.jpa.JPAConfig;
import ru.timestop.storage.security.SecurityConfig;
import ru.timestop.storage.web.WebConfig;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@Import({SecurityConfig.class, JPAConfig.class, WebConfig.class})
public class UploadingFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadingFilesApplication.class, args);
    }
}
