package com.thiru.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiru.entities.Student;
import com.thiru.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("John");
        student.setBranch("CSE");
        student.setRegNumber("23G31A0536");

        // Mock the service call
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/createstudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"John\",\"branch\":\"CSE\",\"regNumber\":\"23G31A0536\"}"))
                .andExpect(status().isOk())   // 200
                .andExpect(content().string("Student created successfully"));
    }


    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Updated Thiru");
        student.setBranch("ECE");

        when(studentService.updateStudentById(eq(1), any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/api/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().string("Student with ID 1 has been updated successfully."));
    }

    @Test
    void testPatchStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Partially Updated");
        student.setBranch("EEE");

        when(studentService.updateStudentByPartially(eq(1), any(Student.class))).thenReturn(student);

        mockMvc.perform(patch("/api/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().string("Student with ID 1 has been updated successfully."));
    }

    @Test
    void testDeleteStudent() throws Exception {
        int studentId = 1;

        // Since service method is NOT void (returns String)
        when(studentService.deleteStudentById(studentId))
                .thenReturn("Student with ID " + studentId + " has been deleted successfully.");

        mockMvc.perform(delete("/api/student/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Student with ID " + studentId + " has been deleted successfully."));
    }
}