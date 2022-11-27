package com.example.practice;

import com.example.practice.dao.ApplicationDao;
import com.example.practice.models.StudentGrades;
import com.example.practice.models.UniversityStudent;
import com.example.practice.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MockAnnotationTest {

	@Autowired
	ApplicationContext context;

	@Autowired
	UniversityStudent universityStudent;

	@Autowired
	StudentGrades studentGrades;

	@MockBean
	private ApplicationDao applicationDao;

	@Autowired
	private ApplicationService applicationService;

	@BeforeEach
	public void beforeEach() {
		universityStudent.setFirstName("Kate");
		universityStudent.setLastName("Bryan");
		universityStudent.setEmailAddress("testing@test.com");
		universityStudent.setStudentGrades(studentGrades);
	}

	@DisplayName("When & Verify")
	@Test
	public void assertEqualsTestAddGrades() {
		// this is the mock response - we're setting the "getMathGradeResults" to 100.00
		when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);

		assertEquals(100, applicationService.addGradeResultsForSingleClass(universityStudent.getStudentGrades().getMathGradeResults()));

		verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
		verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
	}

	@DisplayName("Find GPA")
	@Test
	public void assertEqualsTestFindGpa() {
		when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

		assertEquals(88.31, applicationService.findGradePointAverage(universityStudent.getStudentGrades().getMathGradeResults()));

		verify(applicationDao).findGradePointAverage(studentGrades.getMathGradeResults());
		verify(applicationDao, times(1)).findGradePointAverage(studentGrades.getMathGradeResults());
	}

	@DisplayName("Not Null")
	@Test
	public void testAssertNotNull() {
		when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

		assertNotNull(applicationService.checkNull(universityStudent.getStudentGrades().getMathGradeResults()));
	};

	@DisplayName("Throws an Exception")
	@Test
	public void throwsAnException() {
		UniversityStudent nullStudent = (UniversityStudent) context.getBean("universityStudent");

		doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

		assertThrows(RuntimeException.class, () -> {applicationService.checkNull(nullStudent);
		});

		verify(applicationDao, times(1)).checkNull(nullStudent);
	}

	@DisplayName("Multiple Stubbing")
	@Test
	public void stubbingConsecutiveCalls() {
		UniversityStudent nullStudent = (UniversityStudent) context.getBean("universityStudent");

		when(applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException()).thenReturn("Don't throw exception again");

		assertThrows(RuntimeException.class, () -> {applicationService.checkNull(nullStudent);
		});

		assertEquals("Don't throw exception again", applicationService.checkNull(nullStudent));

		verify(applicationDao, times(2)).checkNull(nullStudent);
	}
}
