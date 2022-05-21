package com.gaggle.sdetassessment;

import com.gaggle.sdetassessment.School;
import com.gaggle.sdetassessment.sdk.SchoolSdk;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("smoke")
class SdetAssessmentApplicationTestsIT {
    public int schoolId = 12;
    private School payload;
    private SchoolSdk schoolSdk;

    @BeforeAll
    void setup() {
        schoolSdk = new SchoolSdk();
        payload = new School(schoolId, "York", 1100, "principal@york.com");
    }

    @Test
    void contextLoads() {
    }

    private void schoolValidation(School actualSchool, School expectedSchool) {
        assertThat(actualSchool).isNotNull();
        Integer schoolId = actualSchool.getSchoolId();
        assertThat(schoolId).isInstanceOf(Integer.class);
        assertThat(expectedSchool.getEmailAddress()).isEqualTo(actualSchool.getEmailAddress());
        assertThat(expectedSchool.getStudentCount()).isEqualTo(actualSchool.getStudentCount());
        assertThat(expectedSchool.getSchoolName()).isEqualTo(actualSchool.getSchoolName());
    }

    @Test
    void createSchoolTest() {
        School createdSchool = schoolSdk.createSchool(payload);
        assertThat(createdSchool).isNotNull();
        schoolValidation(createdSchool, payload);
        //Retrieve the created data. Do not just rely on the response returned by the create call.
        School retrievedSchool = schoolSdk.getSchool(schoolId);
        schoolValidation(retrievedSchool, payload);
    }

    @Test
    void schoolRetrieveTest() {
        List<School> allSchools = schoolSdk.getAllSchools();
        assertThat(allSchools).isNotNull();
        assertThat(allSchools).isInstanceOf(List.class);

        allSchools.forEach(school -> {
            assertThat(school).isNotNull();
            assertThat(school.getSchoolId()).isInstanceOf(Integer.class);
            assertThat(school.getStudentCount()).isInstanceOf(Integer.class);
            assertThat(school.getEmailAddress()).isNotEmpty();
            assertThat(school.getSchoolName()).isNotEmpty();

            School getSchoolResponse = schoolSdk.getSchool(school.getSchoolId());
            schoolValidation(getSchoolResponse, school);
        });
    }

    @Test
    void getInvalidSchool() {
        Response getSchoolResponse = schoolSdk.getSchool(50, 500);
        // Status code should ideally not be 500.
        // 500 -> is for server side errors.
        // In this case, user is clearly passing an invalid school ID. So it's not a server error.
        // Status code should be in 4XX series. For e.g 400: Bad Request or 404: Requested resource not found.

        // Also, there is another bug in this response where the stacktrace of the code is returned in the response body.
        // Internal stack traces should not be exposed in public APIs. It's a security as well as usability issue.
    }

    @Disabled
    @Test
    void retrieveIndividualDataNegative() {
        Response response = given().contentType(ContentType.JSON)
                .pathParam("schoolId", 50)
                .when()
                .get("/schools/{schoolId}").then()
                .assertThat().statusCode(500).log().all().extract().response();
    }

    @Test
    void updateData() throws JSONException {

        School payload = new School(schoolId, "Devry", 500, "principal@devry.com");

        Response response = given().contentType(ContentType.JSON)
                .pathParam("schoolId", schoolId)
                .body(payload, ObjectMapperType.GSON)
                .when().post("/schools/{schoolId}")
                .then().statusCode(200).log().all().extract().response();

        School responseBody = response.body().as(School.class, ObjectMapperType.GSON);
        Assertions.assertEquals(responseBody, payload);
    }

    @Disabled
    @Test
    void updateDataNegative() throws JSONException {

        School payload = new School(schoolId, "Devry", 500, "principal@devry.com");

        Response response = given().contentType(ContentType.JSON)
                .pathParam("schoolId", schoolId)
                .body(payload, ObjectMapperType.GSON)
                .when().post("/schools/{schoolId}")
                .then().statusCode(500).log().all().extract().response();
    }
}

