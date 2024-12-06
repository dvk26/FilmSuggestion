package com.machineLearning.filmSuggestionWeb.util;

import org.springframework.stereotype.Component;

@Component
public class PreprocessTitleUtil {

    public String preprocessTitle(String title){
        StringBuilder sb = new StringBuilder(" ");
        if(title.contains("(")){
            int index= title.indexOf("(");
            title= title.substring(0,index).strip();
        }
        sb.append(title);
        System.out.println(sb.toString());
        return sb.toString();
    }
}
