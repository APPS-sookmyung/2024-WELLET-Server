package WELLET.welletServer.ocr.service;

import WELLET.welletServer.files.FileUploader;
import WELLET.welletServer.ocr.exception.OcrErrorCode;
import WELLET.welletServer.ocr.exception.OcrException;
import com.google.cloud.vision.v1.*;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OcrService {

    private final FileUploader fileUploader;

    public String detectTextGcs(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String OCR_KEY = "ocr/";
            String url = fileUploader.uploadFile(file, OCR_KEY);
            detectTextGcs(url);
            return "성공";
        }
        return "없음";
    }

    public static void detectTextGcs(String gcsPath) throws IOException {

//        Dotenv dotenv = Dotenv.load();
//        String googleCredentialsPath = dotenv.get("GOOGLE_APPLICATION_CREDENTIALS");
//        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", googleCredentialsPath);

        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
        BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = response.getResponsesList();

        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                log.error("Error: {}", res.getError().getMessage());
                throw new OcrException(OcrErrorCode.FAILED_OCR_PROCESSING);
            }

            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                System.out.format("Text: %s%n", annotation.getDescription());
//                System.out.format("Position : %s%n", annotation.getBoundingPoly());

            }
        }
    }
    }
}