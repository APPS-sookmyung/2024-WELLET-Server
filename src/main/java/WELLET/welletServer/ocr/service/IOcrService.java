package WELLET.welletServer.ocr.service;

import WELLET.welletServer.ocr.dto.OcrRequest;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

public interface IOcrService {
    String model = "C:/Users/ajung/Documents/GitHub/2024-WELLET-Server/src/main/resources/static/tesseract";

    void getImageToText(OcrRequest dto) throws TesseractException, IOException;
}
