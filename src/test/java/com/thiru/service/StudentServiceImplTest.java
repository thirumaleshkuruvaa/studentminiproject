package com.thiru.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiru.ExceptionalHandling.StudentNotFoundException;
import com.thiru.entities.Student;
import com.thiru.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1);
        student.setName("Thirumalesh");
        student.setBranch("CSE");
        student.setRegNumber("23G31A0536");
    }

    // 1. Test Create Student
    @Test
    void testCreateStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student saved = studentService.createStudent(student);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(1);
        assertThat(saved.getName()).isEqualTo("Thirumalesh");
        verify(studentRepository, times(1)).save(student);
    }

    // 2. Test Get Student by ID - Found
    @Test
    void testGetStudentById_WhenFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1);

        assertThat(result).isNotNull();
        assertThat(result.getBranch()).isEqualTo("CSE");
    }

    // 3. Test Get Student by ID - Not Found
    @Test
    void testGetStudentById_WhenNotFound() {
        when(studentRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.getStudentById(2));
    }

    // 4. Test Update Student - Found
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

    // 5. Test Update Student - Not Found
    @Test
    void testUpdateStudentById_WhenStudentNotFound() {
        when(studentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.updateStudentById(99, student));
    }

    // 6. Test Determine Branch and Name - From DB
    @Test
    void testDetermineBranchAndName_FromDB() {
        when(studentRepository.findByRegNumber("23G31A0536"))
                .thenReturn(Optional.of(student));

        String result = studentService.determineBranchAndName("23G31A0536");

        assertThat(result).isEqualTo("Thirumalesh, CSE");
    }

    // 7. Test Determine Branch and Name - By Pattern (fallback)
    @Test
    void testDetermineBranchAndName_ByPattern() {
        String result = studentService.determineBranchAndName("23G31A0495");

        assertThat(result).isEqualTo("UNKNOWN, ECE");
    }

    // 8. Test Delete Student
    @Test
    void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById(1);

        studentService.deleteStudentById(1);

        verify(studentRepository, times(1)).deleteById(1);
    }
}


