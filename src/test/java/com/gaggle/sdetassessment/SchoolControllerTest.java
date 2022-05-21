package com.gaggle.sdetassessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchoolControllerTest {

    @Autowired private SchoolController schoolController;

    @Test
    void testListAllSchools() {
        Collection<School> schoolCollection = schoolController.listAllSchools();
        assertNotNull(schoolCollection);
        for (School school : schoolCollection) {
            assertNotNull(school.getSchoolName());
            assertNotNull(school.getEmailAddress());
            assertTrue(school.getEmailAddress().startsWith("principal"));

            // test order is not guaranteed, so if a school was added before this test was run, it would fail
            if (school.getSchoolId() < SchoolController.NUM_SCHOOLS) {
                assertEquals(SchoolController.SCHOOL_NAMES[school.getSchoolId()], school.getSchoolName());
            }
        }
    }

    @Test
    void testGetSchoolByIdReturnsTheSameSchoolFromTheList() {
        Collection<School> schoolCollection = schoolController.listAllSchools();
        assertNotNull(schoolCollection);
        for (School schoolToCheck : schoolCollection) {
            try {
                School schoolFromSingleEndpoint = schoolController.getSchool(schoolToCheck.getSchoolId());
                assertEquals(schoolFromSingleEndpoint, schoolToCheck);
            } catch (NotFoundException e) {
                fail(e);
            }
        }
    }

    @Test
    void testGetSchoolByIdReturnsTheRightValueFromArray() {
        try {
            int i = SchoolController.SCHOOL_NAMES.length / 2;
            School schoolFromSingleEndpoint = schoolController.getSchool(i);
            assertEquals(schoolFromSingleEndpoint.getSchoolName(), SchoolController.SCHOOL_NAMES[i]);
            assertNotNull(schoolFromSingleEndpoint.getEmailAddress());
            assertTrue(schoolFromSingleEndpoint.getEmailAddress().startsWith("principal"));
        } catch (NotFoundException nfe) {
            fail(nfe);
        }
    }

    @Test
    void testGetSchoolByIdThrowsNotFound() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Collection<School> schoolCollection = schoolController.listAllSchools();
            assertNotNull(schoolCollection);
            School schoolFromSingleEndpoint = schoolController.getSchool(schoolCollection.size() + 1);
        });
    }

    @Test
    void testUpdateSchool() {
        try {
            Collection<School> schoolCollectionBefore = schoolController.listAllSchools();
            assertNotNull(schoolCollectionBefore);
            int startingSize = schoolCollectionBefore.size();

            int schoolId = startingSize / 2;
            School saveOriginalSchool = schoolController.getSchool(schoolId);

            School newSchool = new School(schoolId, "SpongeBob", 8765,
                    "plankton@spongebob.edu");
            schoolController.updateSchool(schoolId, newSchool);

            Collection<School> schoolCollectionAfter = schoolController.listAllSchools();
            assertNotNull(schoolCollectionAfter);
            int endingSize = schoolCollectionAfter.size();
            assertEquals(startingSize, endingSize);

            School schoolFromSingleEndpoint = schoolController.getSchool(schoolId);
            assertNotNull(schoolFromSingleEndpoint.getSchoolName());
            assertNotNull(schoolFromSingleEndpoint.getEmailAddress());
            assertEquals(schoolId, schoolFromSingleEndpoint.getSchoolId());
            assertEquals(8765, schoolFromSingleEndpoint.getStudentCount());

            // return the school back to its original state so other tests pass
            schoolController.updateSchool(schoolId, saveOriginalSchool);

        } catch (NotFoundException nfe) {
            fail(nfe);
        }
    }

    @Test
    void testUpdateSchoolByIdThrowsNotFound() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Collection<School> schoolCollection = schoolController.listAllSchools();
            assertNotNull(schoolCollection);
            School newSchool = new School(schoolCollection.size(), "SpongeBob", 8765,
                    "plankton@spongebob.edu");
            schoolController.updateSchool(schoolCollection.size(), newSchool);
        });
    }

    @Test
    void addSchool() {

        Collection<School> schoolCollectionBefore = schoolController.listAllSchools();
        assertNotNull(schoolCollectionBefore);
        int startingSize = schoolCollectionBefore.size();

        School school = new School(startingSize + 1, "SpongeBob", 8765,
                "plankton@spongebob.edu");
        schoolController.addSchool(school);

        Collection<School> schoolCollectionAfter = schoolController.listAllSchools();
        assertNotNull(schoolCollectionAfter);
        int endingSize = schoolCollectionAfter.size();

        assertEquals(startingSize + 1, endingSize);

        try {
            School retriveSchoolBack = schoolController.getSchool(startingSize + 1);
            assertNotNull(retriveSchoolBack.getSchoolName());
            assertNotNull(retriveSchoolBack.getEmailAddress());
            assertEquals("SpongeBob", retriveSchoolBack.getSchoolName());
            assertEquals(startingSize + 1, retriveSchoolBack.getSchoolId());
            assertEquals(8765, retriveSchoolBack.getStudentCount());
            assertEquals("plankton@spongebob.edu", retriveSchoolBack.getEmailAddress());
        } catch (NotFoundException e) {
            fail(e);
        }
    }
}