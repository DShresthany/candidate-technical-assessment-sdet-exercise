package com.gaggle.sdetassessment;


import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "schools")
public class SchoolController {
    public static final String[] SCHOOL_NAMES = {"Washington", "Jefferson", "Franklin", "Addams", "Kennedy",
                    "Lincoln", "Taft", "Filmore", "Roosevelt", "Grant"};
    public static final int NUM_SCHOOLS = SCHOOL_NAMES.length;
    private static final HashMap<Integer, School> SCHOOL_REPOSITORY = new HashMap<>();

    @GetMapping
    public Collection<School> listAllSchools(){
        return generateSchoolList();
    }

    @GetMapping(path = "/{id}")
    public School getSchool(@PathVariable int id) throws NotFoundException {
        School school = SCHOOL_REPOSITORY.get(id);
        if (school == null) {
            throw new NotFoundException();
        }
        return school;
    }

    @PostMapping(path = "/{id}")
    public School updateSchool(@PathVariable int id, @RequestBody School incomingSchool) throws NotFoundException {
        School schoolInRepo = getSchool(id);

        schoolInRepo.setSchoolName(incomingSchool.getSchoolName());
        schoolInRepo.setEmailAddress(incomingSchool.getEmailAddress());
        schoolInRepo.setStudentCount(incomingSchool.getStudentCount());

        return schoolInRepo;
    }

    @PutMapping
    public School addSchool(@RequestBody School school) {
        SCHOOL_REPOSITORY.put(school.getSchoolId(), school);
        return school;
    }

    private Collection<School> generateSchoolList()
    {
        if (SCHOOL_REPOSITORY.isEmpty()) {
            for (int i = 0; i < NUM_SCHOOLS; i++) {
                SCHOOL_REPOSITORY.put(i, new School(i, SCHOOL_NAMES[i], (i + 3) * 250,
                        "principal@" + SCHOOL_NAMES[i].toLowerCase(Locale.ROOT) + ".edu"));
            }
        }
        return SCHOOL_REPOSITORY.values();
    }
}
