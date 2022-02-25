package com.hsbc.qe.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class GsonUtils {

    Gson gson;
    public GsonUtils() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gson = gsonBuilder.create();
    }

    public <T> T jsonToPojo(String filePathjson, Class<T> classOfT) {
        try(Reader reader = new FileReader(ClassLoader.getSystemResource(filePathjson).getPath())) {
            return gson.fromJson(reader, classOfT);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        } catch (IOException e) {
            throw new RuntimeException("Input file not found");
        }
    }

    public String objToJson(Object obj) {
        return gson.toJson(obj);
    }

}

