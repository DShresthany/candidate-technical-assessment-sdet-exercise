package com.gaggle.sdetassessment;

import com.gaggle.sdetassessment.sdk.SchoolSdk;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
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

    private void schoolValidation(School actualSchool, School expectedSchool) {
        /**
         * for validating the expected school object versus actual school object created
         * */

        assertThat(actualSchool).isNotNull();
        Integer schoolId = actualSchool.getSchoolId();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(schoolId).isInstanceOf(Integer.class);
        softly.assertThat(actualSchool.getEmailAddress()).as("EmailAddress").isEqualTo(expectedSchool.getEmailAddress());
        softly.assertThat(actualSchool.getStudentCount()).as("StudentCount").isEqualTo(expectedSchool.getStudentCount());
        softly.assertThat(actualSchool.getSchoolName()).as("SchoolName").isEqualTo(expectedSchool.getSchoolName());
        softly.assertAll();
    }
    @Test
    void contextLoads() {
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
        Response getSchoolResponse = schoolSdk.getSchool(999, 500);
        // Status code should ideally not be 500.
        // 500 -> is for server side errors.
        // In this case, user is clearly passing an invalid school ID. So it's not a server error.
        // Status code should be in 4XX series. For e.g 400: Bad Request or 404: Requested resource not found.

        // Also, there is another bug in this response where the stacktrace of the code is returned in the response body.
        // Internal stack traces should not be exposed in public APIs. It's a security as well as usability issue.
        System.out.println("getSchoolResponse.asPrettyString() = " + getSchoolResponse.asPrettyString());
        assertThat(getSchoolResponse.body().asString()).contains("NotFoundException");
    }

    @Test
    void updateSchool() {
        School updatePayload = new School(schoolId, "Devry", 400, "principal@Devry.com");
        School updatedSchool = schoolSdk.updateSchool(updatePayload, schoolId);

        assertThat(updatedSchool).isNotNull();

        schoolValidation(updatedSchool, updatePayload);
        //Retrieve the created data. Do not just rely on the response returned by the create call.
        School retrievedSchool = schoolSdk.getSchool(schoolId);
        schoolValidation(retrievedSchool, updatedSchool);
    }

    @Test
    void updateSchoolNegative() {
        int invalidSchoolId = 999;
        School updatePayload = new School(invalidSchoolId, "Devry", 400, "principal@Devry.com");
        Response updatedSchool = schoolSdk.updateSchool(updatePayload, invalidSchoolId, 500);
        // Status code should ideally not be 500.
        // 500 -> is for server side errors.
        // In this case, user is clearly passing an invalid school ID. So it's not a server error.
        // Status code should be in 4XX series. For e.g 400: Bad Request or 404: Requested resource not found.

        // Also, there is another bug in this response where the stacktrace of the code is returned in the response body.
        // Internal stack traces should not be exposed in public APIs. It's a security as well as usability issue.
        assertThat(updatedSchool.body().asString()).contains("NotFoundException");
    }
}