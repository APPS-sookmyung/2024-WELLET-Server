package WELLET.welletServer.ocr.controller;

import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.ocr.dto.OcrRequest;
import WELLET.welletServer.ocr.service.OcrService;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
@RequiredArgsConstructor
public class OcrController {
    private final OcrService ocrService;

    @PostMapping
    public BasicResponse<String> ocr (@ModelAttribute OcrRequest dto) throws TesseractException, IOException {
        ocrService.getImageToText(dto);
        return ResponseUtil.success("텍스트 추출에 성공하였습니다.");
    }
}
