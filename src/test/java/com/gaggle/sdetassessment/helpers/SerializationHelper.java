package com.gaggle.sdetassessment.helpers;

import com.google.gson.Gson;

public class SerializationHelper {
    private final static Gson gson = new Gson();

    public static <T> String toJsonString(T payload) {
        return gson.toJson(payload);
    }
}
