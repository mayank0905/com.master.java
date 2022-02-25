package com.hsbc.qe.common.utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class JsonUtil {

    public static DocumentContext getDocumentContext(String json) {
        return JsonPath.parse(json);
    }

    public static DocumentContext getDocumentContext(InputStream json) {
        return JsonPath.parse(json);
    }

    public static String getNodeValue(String json, String path){
        return Objects.isNull(getDocumentContext(json).read("$."+path)) ? null : getDocumentContext(json).read("$."+path).toString();
    }

    public static String getNodeValue(InputStream json, String path){
        return Objects.isNull(getDocumentContext(json).read("$."+path)) ? null : getDocumentContext(json).read("$."+path).toString();
    }

    public static String setNodeValue(String json, String path, String valueToReplace){
        return getDocumentContext(json).set("$."+path, valueToReplace).jsonString();
    }

    public File getJsonFile(final String jsonFileName) throws URISyntaxException {

        URL url = this.getClass().getClassLoader().getResource(jsonFileName);
        if(url != null) {
            File jsonFile = new File(url.toURI());
            System.out.println("Reading jsonFile=" + jsonFile);
            return jsonFile;
        }
        return null;
    }

    public String readJSONContent(String mappingFileName) throws IOException, URISyntaxException {
        File jsonFile = this.getJsonFile(mappingFileName);
        Map<String, String> dataMap = null;
        if(jsonFile != null) {
            dataMap = JsonPath.parse(jsonFile).read("$");
        }
        return mappingFileName;
    }

    public String readJSONFile(String filePath) throws FileNotFoundException {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        return Objects.isNull(getDocumentContext(targetStream).read("$.data")) ? null : getDocumentContext(targetStream).read("$.data").toString();
    }
}
