package com.redis.controller;

import com.redis.models.Student;
import com.redis.service.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping(value = "/studentView", method = RequestMethod.GET)
    public ModelAndView studentView(ModelAndView model) {
        Iterable<Student> students = studentRepository.findAll();
        model.setViewName("students");
        model.addObject("student", new Student());
        model.addObject("list", students);
        return model;
    }
    @GetMapping(value = "/getStudent-{id}", produces = "application/json")
    public ResponseEntity<Student> findByTitle(@PathVariable String id) {
        Student retrievedStudent = studentRepository.findById(id).get();
        return ResponseEntity.ok(retrievedStudent);
    }

    @PostMapping(path = "/addStudent")
    public String addStudent(@ModelAttribute Student student) {
        try {
            studentRepository.save(student);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return "redirect:/studentView";
        }
    }

    @GetMapping(value = "/editStudent/{id}")
    public ModelAndView editStudent(@PathVariable String id, ModelAndView model) {
        Iterable<Student> students = studentRepository.findAll();
        Optional<Student> student = studentRepository.findById(id);
        model.setViewName("students");
        model.addObject("student", student.get());
        model.addObject("list", students);
        return model;
    }

    @GetMapping(value = "/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
        return "redirect:/studentView";
    }

}
