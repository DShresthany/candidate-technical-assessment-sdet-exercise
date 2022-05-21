package com.gaggle.sdetassessment;

import java.util.Objects;
import java.util.UUID;

public class School {
    private int schoolId;
    private String schoolName;
    private int studentCount;
    private String emailAddress;

    public School(int schoolId, String schoolName, int studentCount, String emailAddress) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.studentCount = studentCount;
        this.emailAddress = emailAddress;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.schoolName = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return getSchoolId() == school.getSchoolId() && getStudentCount() == school.getStudentCount() && Objects.equals(getSchoolName(), school.getSchoolName()) && Objects.equals(getEmailAddress(), school.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSchoolId(), getSchoolName(), getStudentCount(), getEmailAddress());
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", studentCount=" + studentCount +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
