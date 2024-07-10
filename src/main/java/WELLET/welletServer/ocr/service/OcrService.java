package WELLET.welletServer.ocr.service;

import WELLET.welletServer.ocr.dto.OcrRequest;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@Transactional
public class OcrService implements IOcrService {

    private static final String filePath =  "C:/Users/ajung/Documents/GitHub/2024-WELLET-Server/src/main/resources/static/testFile.png";
    @Override
    @Transactional
    public void getImageToText(OcrRequest dto) throws TesseractException, IOException {

        // 서버에 파일 저장 -> url 받아오는 로직 필요

        File file = new File(filePath);

        ITesseract instance = new Tesseract();
        instance.setDatapath(IOcrService.model);
        instance.setLanguage("kor");
        String ImageToText = instance.doOCR(file);

        System.out.println(ImageToText);
    }
}
