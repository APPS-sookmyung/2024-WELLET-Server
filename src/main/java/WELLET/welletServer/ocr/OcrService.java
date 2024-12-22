package WELLET.welletServer.ocr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {
    @Value("${naver.ocr_url}")
    private String url;

    @Value("${naver.ocr_secretKey}")
    private String secretKey;

    /**
     * 네이버 OCR API를 호출하여 JSON 객체 반환
     * @param file MultipartFile 업로드 파일
     * @param ext 확장자
     * @return JSON 객체 (OCR 결과)
     */
    public JSONObject callApi(MultipartFile file, String ext) {
        String apiURL = url;
        JSONObject resultJson = null;


        log.info("callApi Start!");

        try {
            // URL 및 연결 설정
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");

            // Multipart 데이터 설정
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            // JSON 요청 데이터 작성
            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());

            JSONObject image = new JSONObject();
            image.put("format", ext);
            image.put("name", "demo");

            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);

            // 연결 및 데이터 전송
            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            writeMultiPart(wr, json.toString(), file, boundary);
            wr.close();

            // 응답 처리
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // JSON 응답 데이터 반환
            resultJson = parseJson(response.toString());

        } catch (Exception e) {
            log.error("Error during OCR API call", e);
        }

        return resultJson;
    }

    public JSONObject jsonExtract(JSONObject jsonObject) {
        // 최종 결과 JSON 객체 생성
        JSONObject resultJson = new JSONObject();

        // 'images' 배열 추출
        JSONArray imagesArray = (JSONArray) jsonObject.get("images");
        if (imagesArray == null || imagesArray.isEmpty()) {
            resultJson.put("error", "No images found in response.");
            return resultJson;
        }

        // 첫 번째 이미지 객체 추출
        JSONObject imageObject = (JSONObject) imagesArray.get(0);
        if (imageObject == null) {
            resultJson.put("error", "No image data found.");
            return resultJson;
        }

        // 'nameCard' 객체 추출
        JSONObject nameCardObject = (JSONObject) imageObject.get("nameCard");
        if (nameCardObject == null) {
            resultJson.put("error", "No nameCard data found.");
            return resultJson;
        }

        // 'result' 객체 추출
        JSONObject resultObject = (JSONObject) nameCardObject.get("result");
        if (resultObject == null) {
            resultJson.put("error", "No result data found.");
            return resultJson;
        }

        // 필요한 필드 추출
        String[] keysToExtract = {"name", "company", "position", "department", "mobile", "email", "tel", "fax", "address", "homepage"};

        for (String key : keysToExtract) {
            JSONArray fieldArray = (JSONArray) resultObject.get(key);
            if (fieldArray != null && !fieldArray.isEmpty()) {
                JSONObject fieldObject = (JSONObject) fieldArray.get(0);
                String text = (String) fieldObject.get("text");
                resultJson.put(key, text);
            }
        }

        return resultJson; // JSON 객체 반환
    }


    /**
     * Multipart 데이터 작성
     * @param out OutputStream 데이터 출력 스트림
     * @param jsonMessage JSON 요청 메시지
     * @param file MultipartFile 업로드 파일
     * @param boundary 경계 문자열
     */
    private static void writeMultiPart(OutputStream out, String jsonMessage, MultipartFile file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage).append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && !file.isEmpty()) {
            sb.setLength(0);
            sb.append("--").append(boundary).append("\r\n");
            sb.append("Content-Disposition:form-data; name=\"file\"; filename=\"").append(file.getOriginalFilename()).append("\"\r\n");
            sb.append("Content-Type: application/octet-stream\r\n\r\n");

            out.write(sb.toString().getBytes("UTF-8"));
            out.flush();

            InputStream inputStream = file.getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.write("\r\n".getBytes("UTF-8"));
        }
        out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        out.flush();
    }

    /**
     * JSON 문자열을 JSONObject로 변환
     * @param response JSON 응답 문자열
     * @return JSONObject
     */
    private static JSONObject parseJson(String response) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(response);
        } catch (Exception e) {
            throw new RuntimeException("JSON Parsing Error: " + e.getMessage());
        }
    }
}
