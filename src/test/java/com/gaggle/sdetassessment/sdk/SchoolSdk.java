package com.gaggle.sdetassessment.sdk;

import com.gaggle.sdetassessment.School;
import com.gaggle.sdetassessment.base.RestClient;
import com.gaggle.sdetassessment.helpers.SerializationHelper;
import com.google.gson.reflect.TypeToken;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * A client side SDK that expose HTTP interactions with the Controllers.
 */
public class SchoolSdk {

    public School createSchool(School payload) {
        return RestClient
                .put("/schools", SerializationHelper.toJsonString(payload), HttpStatus.SC_OK)
                .body().as(School.class, ObjectMapperType.GSON);
    }

    public School updateSchool(School payload, String schoolId) {
        return RestClient
                .post("/schools/" + schoolId, SerializationHelper.toJsonString(payload), HttpStatus.SC_OK)
                .body().as(School.class, ObjectMapperType.GSON);
    }

    public School getSchool(Integer schoolId) {
        return RestClient
                .get("/schools/" + schoolId, HttpStatus.SC_OK)
                .body().as(School.class, ObjectMapperType.GSON);
    }

    public Response getSchool(Integer schoolId, Integer expectedStatusCode) {
        return RestClient
                .get("/schools/" + schoolId, expectedStatusCode);
    }

    public List<School> getAllSchools() {
        return RestClient
                .get("/schools", HttpStatus.SC_OK)
                .body().as(new TypeToken<ArrayList<School>>() {
                }.getType(), ObjectMapperType.GSON);
    }

    public void deleteSchool() {
        //TODO
    }
}
