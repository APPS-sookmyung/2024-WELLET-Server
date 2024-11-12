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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<String> create(@Valid @ModelAttribute OcrSaveDto dto) throws IOException {
        String secretkey = "b3lzZ1RmbEZpR1VLcm5VQXBFVm5RYkdoZVdtSVBwbW4=";

        if (dto.file() != null && !dto.file().isEmpty()) {
            String OCR_KEY = "ocr/";
            String url = fileUploader.uploadFile(dto.file(), OCR_KEY);
            return OcrService.callApi("POST", url, secretkey, "png");
        } else {
            return null;
        }
    }
}
