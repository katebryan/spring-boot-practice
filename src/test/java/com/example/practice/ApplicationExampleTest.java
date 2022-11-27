package com.example.practice;

import com.example.practice.models.StudentGrades;
import com.example.practice.models.UniversityStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationExampleTest {

    private static int count = 0;

    @Value("${info.school.name}")
    private String schoolName;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    UniversityStudent universityStudent;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach() {
        count = count + 1;

        universityStudent.setFirstName("Kate");
        universityStudent.setLastName("Bryan");
        universityStudent.setEmailAddress("testing@test.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 95.00, 87.50)));
        universityStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("Add grade results for student grades")
    @Test
    public void addGradeResultsForStudentGrades() {
        assertEquals(282.5, studentGrades.addGradeResultsForSingleClass(
                universityStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Add grade results for student grades - not equal")
    @Test
    public void addGradeResultsForStudentGradesAssertNotEquals() {
        assertNotEquals(10, studentGrades.addGradeResultsForSingleClass(
                universityStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Is grade greater")
    @Test
    public void isGradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(90, 75));
    }

    @DisplayName("Is grade greater - false")
    @Test
    public void isGradeGreaterStudentGradesAssertFalse() {
        assertFalse(studentGrades.isGradeGreater(89, 92));
    }

    @DisplayName("Check null for student grades")
    @Test
    public void checkNullForStudentGrades() {
        assertNotNull(studentGrades.checkNull(
                universityStudent.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Create student without grade init")
    @Test
    public void createStudentWithoutGradesInit() {
    UniversityStudent studentTwo = context.getBean("universityStudent", UniversityStudent.class);
    studentTwo.setFirstName("Softie");
    studentTwo.setLastName("Cheese");
    studentTwo.setEmailAddress("softie@cheese.com");

    assertNotNull(studentTwo.getFirstName());
    assertNotNull(studentTwo.getLastName());
    assertNotNull(studentTwo.getEmailAddress());
    assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsArePrototypes() {
        UniversityStudent studentTwo = context.getBean("universityStudent", UniversityStudent.class);

        assertNotSame(universityStudent, studentTwo);
    }

    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage() {
        assertAll("Testing all assertEquals",
                () -> assertEquals(282.5, studentGrades.addGradeResultsForSingleClass(
                        universityStudent.getStudentGrades().getMathGradeResults())),
                () -> assertEquals(94.17, studentGrades.findGradePointAverage(
                        universityStudent.getStudentGrades().getMathGradeResults()
                ))
        );
    }

}
