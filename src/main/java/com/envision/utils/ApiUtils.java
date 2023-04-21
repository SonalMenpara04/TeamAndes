package com.envision.utils;

import io.restassured.http.Method;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApiUtils {

    public static String getStringBody(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static String getBodyWithReplacedValues(String field, String valueToReplaceString,String filePath) throws IOException {
        String str =new String(Files.readAllBytes(Paths.get(filePath)));
        str = str.replaceAll(field,valueToReplaceString);
        return str;
    }

    public static Method getMethodType(String value){
        if(value.equalsIgnoreCase("POST")){
            return Method.POST;
        }else if(value.equalsIgnoreCase("GET")){
            return Method.GET;
        }else if(value.equalsIgnoreCase("PUT")){
            return Method.PUT;
        }else if(value.equalsIgnoreCase("DELETE")){
            return Method.DELETE;
        }else if(value.equalsIgnoreCase("PATCH")){
            return Method.PATCH;
        }
        return null;
    }
}
