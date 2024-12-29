package com.machineLearning.filmSuggestionWeb.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ResponseToJsonUtil {

    public String convertResponseToJson(String response){
        JSONObject jsonObject = new JSONObject(response);
        String text = jsonObject
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");
        Pattern pattern = Pattern.compile("\\[(\\{.*?})\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        text = text.replace("<em>","");
        text = text.replace("</em>","");
        text = text.replace("/n", "");
        if(text.charAt(text.length()-1)!=']'){
            int  lastIndex1= text.lastIndexOf("{");
            text=text.substring(0,lastIndex1);

            int lastIndex2 = text.lastIndexOf(",");
            text=text.substring(0,lastIndex2);
            text+="]";
        }
        // Thay nháy đơn thành nháy kép để JSON hợp lệ
        text = text.replace("'", "\"");
        text = text.replace("<em>","");
        text = text.replace("</em>","");
        System.out.println(text);
        // Kiểm tra xem chuỗi có phải là JSON hợp lệ hay không
        try {
            // Sử dụng ObjectMapper để chuyển đổi
            ObjectMapper objectMapper = new ObjectMapper();

            // Chuyển chuỗi thành JsonNode
            JsonNode jsonArray = objectMapper.readTree(text);

            // In kết quả
            System.out.println("Mảng JSON hợp lệ:");
            return jsonArray.toPrettyString();
        } catch (Exception e) {
            System.err.println("Lỗi khi chuyển đổi JSON: " + e.getMessage());
            return null;
        }


    }
}
