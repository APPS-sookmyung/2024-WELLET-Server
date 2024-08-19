package WELLET.welletServer.files;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileUploader {
    String uploadFile(MultipartFile file, String path);
    void deleteFile(String url, String path);

    default ObjectMetadata createObjectMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/png");
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    default String generateKey(MultipartFile file, String path) {
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new S3IOException("파일 이름이 존재하지 않습니다.");
        }
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return path + UUID.randomUUID() + "." + ext;
    }
}
