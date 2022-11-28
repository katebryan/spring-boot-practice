package com.example.practice;

import com.example.practice.models.StudentGrades;
import com.example.practice.models.UniversityStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest(classes= MvcTestingPracticeApplication.class)
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    UniversityStudent student;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeforeEach() {
        student.setFirstName("Kate");
        student.setLastName("Bryan");
        student.setEmailAddress("test@test.com");
        student.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(student, "id", 1);
        ReflectionTestUtils.setField(student, "studentGrades",
            new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 85.0,  76.50, 91.75))));
    }

    @Test
    public void getPrivateField() {
        assertEquals(1, ReflectionTestUtils.getField(student, "id"));
    }

    @Test
    public void invokePrivateMethod() {
        assertEquals("Kate 1",
        ReflectionTestUtils.invokeMethod(student, "getFirstNameAndId"), "Fail, private method not called");
    }
}



