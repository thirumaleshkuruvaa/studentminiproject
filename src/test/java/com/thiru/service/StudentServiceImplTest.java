package com.thiru.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiru.ExceptionalHandling.StudentNotFoundException;
import com.thiru.entities.Student;
import com.thiru.repository.StudentRepository;

// This tells JUnit to enable Mockito
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    // Mock: fake object for StudentRepository (no DB needed)
    @Mock
    private StudentRepository studentRepository;

    //  InjectMocks: create StudentServiceImpl and inject mock repo inside
    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student; // sample data we will use

    @BeforeEach
    void setUp() {
    	
        //  Before every test, create a sample student
        student = new Student();
        student.setId(1);
        student.setName("Thirumalesh");
        student.setBranch("CSE");
        student.setRegNumber("23G31A0536");
    }

    //  1. Test Create Student
    @Test
    void testCreateStudent() {
        // Mock behavior: when save() is called, return the same student
       when(studentRepository.save(student)).thenReturn(student);

        // Call service
        Student saved = studentService.createStudent(student);

        // Check results
        Assertions.assertNotNull(saved);
        Assertions.assertTrue(student.getId()==1);
        Assertions.assertEquals(1, saved.getId());
        Assertions.assertEquals(student.getBranch(), saved.getBranch());
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Thirumalesh");

        // Verify repository method was called once
        verify(studentRepository, times(1)).save(student);
    }

    //  2. Test Get Student by ID - Found
    @Test
    void testGetStudentById_WhenFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getBranch()).isEqualTo("CSE");
    }

    // 3. Test Get Student by ID - Not Found
    @Test
    void testGetStudentById_WhenNotFound() {
        when(studentRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.getStudentById(2);

        assertThat(result).isEmpty();
    }

    //  4. Test Update Student - Found
    @Test
    void testUpdateStudentById_WhenStudentExists() {
        Student updatedData = new Student();
        updatedData.setName("Shiva");
        updatedData.setBranch("ECE");

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedData);

        Student result = studentService.updateStudentById(1, updatedData);

        assertThat(result.getName()).isEqualTo("Shiva");
        assertThat(result.getBranch()).isEqualTo("ECE");
    }

    //  5. Test Update Student - Not Found
    @Test
    void testUpdateStudentById_WhenStudentNotFound() {
        when(studentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.updateStudentById(99, student));
    }

    //  6. Test Determine Branch and Name - From DB
    @Test
    void testDetermineBranchAndName_FromDB() {
        when(studentRepository.findByRegNumber("23G31A0536"))
                .thenReturn(Optional.of(student));

        String result = studentService.determineBranchAndName("23G31A0536");

        assertThat(result).isEqualTo("Thirumalesh, CSE");
    }

    //  7. Test Determine Branch and Name - By Pattern (fallback)
    @Test
    void testDetermineBranchAndName_ByPattern() {
        String result = studentService.determineBranchAndName("23G31A0495");

        assertThat(result).isEqualTo("UNKNOWN, ECE");
    }

    //  8. Test Delete Student
    @Test
    void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById(1);

        String response = studentService.deleteStudentById(1);

        assertThat(response).isEqualTo("Student deleted Successfully");
        verify(studentRepository, times(1)).deleteById(1);
    }
}
