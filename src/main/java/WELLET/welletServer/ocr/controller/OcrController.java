package WELLET.welletServer.ocr.controller;

import WELLET.welletServer.files.FileUploader;
import WELLET.welletServer.ocr.dto.OcrSaveDto;
import WELLET.welletServer.ocr.service.OcrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ocr")
@RequiredArgsConstructor
@Tag(name = "ocr 기능")
public class OcrController {

    private final OcrService ocrService;
    private final FileUploader fileUploader;

    @PostMapping
    @Operation(summary = "OCR 사진 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 저장에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "중복된 회원입니다.")
    })
    public List<String> create(@RequestParam("img") MultipartFile img) throws IOException {
        String secretKey = "R1JxTHlHTGhSdmlRTEhTdkF6a2RBTXZDakF1TklVb0k=";

        if (img != null && !img.isEmpty()) {
            // MultipartFile을 File로 변환
            File file = convertMultiPartToFile(img);

            // OCR 서비스 호출
            return OcrService.callApi("POST", secretKey, "png", file);
        } else {
            return null;
        }
    }

    // MultipartFile을 java.io.File로 변환하는 헬퍼 메서드
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}
