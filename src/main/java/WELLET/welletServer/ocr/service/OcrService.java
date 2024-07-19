package WELLET.welletServer.ocr.service;

import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.ocr.dto.OcrRequest;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional
public class OcrService implements IOcrService {

//    private static final String filePath =  "C:/Users/ajung/Documents/GitHub/2024-WELLET-Server/src/main/resources/static/testFile.png";
    private static final String filePath =  "C:/Users/ajung/Documents/GitHub/2024-WELLET-Server/src/main/resources/static/testCardFile.jpg";
    @Override
    @Transactional
    public void getImageToText(OcrRequest dto) throws TesseractException, IOException {

        // 서버에 파일 저장 -> url 받아오는 로직 필요

        File file = new File(filePath);

        ITesseract instance = new Tesseract();
        instance.setDatapath(IOcrService.model);
        instance.setLanguage("eng+kor");
        String imageToText = instance.doOCR(file);
        System.out.println(imageToText);
        extractRegularExpression(imageToText);
    }

    @Transactional
    public void extractRegularExpression(String text) {
        String patternString = "\\S+@\\w+\\.\\w+(\\.\\w+)?";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        String email = "";

        if (matcher.find()) {
            email = String.valueOf(matcher.group());
        }

        System.out.println(email);
    }
}
