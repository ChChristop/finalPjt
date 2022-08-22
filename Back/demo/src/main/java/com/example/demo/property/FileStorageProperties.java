package com.example.demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/* application.yml에 설정한 파일 업로드 경로를 가져옴 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

	private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}