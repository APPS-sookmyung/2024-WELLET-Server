package WELLET.welletServer.ocr;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtill {
    public static List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                map.put(key, jsonObject.get(key));
            }
            list.add(map);
        }
        return list;
    }

}
