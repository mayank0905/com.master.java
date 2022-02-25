package com.hsbc.qe.api.httpoperations;

import com.hsbc.qe.api.customexceptions.CustomException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public enum HttpOperations {

    GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH;

    public static HttpOperations parse(String apiRequest){
        for(HttpOperations httpMethod : values()) {
            if(httpMethod.toString().equalsIgnoreCase(apiRequest))
                return httpMethod;
        }
        throw  new IllegalArgumentException();
    }

    public Response doRequest(RequestSpecification when, String url) {
        switch (this) {
            case GET: return when.get(url);
            case POST: return when.post(url);
            case PUT: return when.put(url);
            case DELETE: return when.delete(url);
            case OPTIONS: return when.options(url);
            case HEAD: return when.head(url);
            case PATCH: return when.patch(url);
        }
        throw new CustomException("The given http method: " + url + "is not valid.");
    }

}
