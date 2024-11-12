package WELLET.welletServer.ocr.service;
import WELLET.welletServer.ocr.JsonUtill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OcrService {
    /**
     * 네이버 ocr api 호출한다
     * @param {string} type 호출 메서드 타입
     * @param {string} filePath 파일 경로
     * @param {string} naver_secretKey 네이버 시크릿키 값
     * @param {string} ext 확장자
     * @returns {List} 추출 text list
     */
    public static List<String> callApi(String type, String naver_secretKey, String ext, File img) {
        String apiURL = "https://y6eirqzxqo.apigw.ntruss.com/custom/v1/35874/c8dd98db253669f0dd43d0b100dbdc5711957a75cde89a38fb513a06809d4272/general";
        List<String> parseData = null;
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod(type);
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", naver_secretKey);

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
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            writeMultiPart(wr, postParams, img, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response);
            parseData = jsonparse(response);

        } catch (Exception e) {
            System.out.println(e);
        }
        return parseData;
    }
    /**
     * writeMultiPart
     * @param {OutputStream} out 데이터를 출력
     * @param {string} jsonMessage 요청 params
     * @param {File} file 요청 파일
     * @param {String} boundary 경계
     */
    private static void writeMultiPart(DataOutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        } else System.out.println("실패");
        out.flush();
    }
    /**
     * 데이터 가공
     * @param {StringBuffer} response 응답값
     * @returns {List} result text list
     */
    private static List<String> jsonparse(StringBuffer response) {
        List<String> result = new ArrayList<>();
        try {
            // JSON 파싱
            JSONParser jp = new JSONParser();
            JSONObject jobj = (JSONObject) jp.parse(response.toString());

            // "images" 키가 있는지 확인하고 JSONArray로 가져오기
            if (jobj.containsKey("images")) {
                JSONArray JSONArrayPerson = (JSONArray) jobj.get("images");

                if (JSONArrayPerson != null && !JSONArrayPerson.isEmpty()) {
                    JSONObject JSONObjImage = (JSONObject) JSONArrayPerson.get(0);
                    JSONArray s = (JSONArray) JSONObjImage.get("fields");

                    if (s != null) {
                        List<Map<String, Object>> m = JsonUtill.getListMapFromJsonArray(s);
                        for (Map<String, Object> as : m) {
                            result.add((String) as.get("inferText"));
                        }
                    } else {
                        System.out.println("No 'fields' array found in the 'images' object.");
                    }
                } else {
                    System.out.println("'images' array is empty or null.");
                }
            } else {
                System.out.println("Key 'images' not found in JSON response");
            }
        } catch (net.minidev.json.parser.ParseException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
        }
        return result;
    }

}

