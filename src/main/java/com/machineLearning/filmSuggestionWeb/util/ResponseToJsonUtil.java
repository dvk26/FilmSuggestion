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
        Pattern pattern = Pattern.compile("```json\\n(\\[.*?\\])\\n```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String jsonArray = matcher.group(1);
            return jsonArray;
        } else {
            System.out.println("No JSON array found.");
        }
        return null;
    }
}
