package WELLET.welletServer.ocr.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public record OcrRequest(MultipartFile file) { }
