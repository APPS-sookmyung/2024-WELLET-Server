package WELLET.welletServer.files;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class TestFileUploader implements FileUploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final String TEST_KEY = "test/";

    @Override
    public String uploadFile(MultipartFile file, String path) {
        ObjectMetadata objectMetadata = createObjectMetaData(file);
        String key = generateKey(file, TEST_KEY);

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            log.error("이미지 업로드 IOException");
            throw new S3IOException(e.getMessage());
        }

        return amazonS3Client.getUrl(bucket, key).toString();
    }

    @Override
    public void deleteFile(String url, String path) {
        try {
            String fileName = url.substring(url.indexOf(TEST_KEY));
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } catch (SdkClientException e) {
            throw new S3IOException(e.getMessage());
        } catch (NullPointerException e) {
            throw new S3IOException("파일이 존재하지 않습니다.");
        }
    }
}
