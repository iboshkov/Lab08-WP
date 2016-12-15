package tech.boshkov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.boshkov.model.Course;
import tech.boshkov.model.Student;
import tech.boshkov.service.CourseService;
import tech.boshkov.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class StudentController {
    @Autowired
    private CourseService mCourseSvc;
    @Autowired
    private StudentService mStudentSvc;

    public StudentController() {
    }

    protected void fixCourseRecursion(Student s) {
        for (Course c : s.getCourses()) {
            c.setStudents(null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/students/")
    public List<Student> getStudents() {
        // Spreci rekurzija
        List<Student> students = (List<Student>) mStudentSvc.findAll();
        for (Student s : students) {
            fixCourseRecursion(s);
        }
        return students;
    }


    @RequestMapping(method = RequestMethod.GET, value = "api/students/{sid}/qualified/")
    public List<Course> getCoursesStudentQualifiesFor(@PathVariable(value="sid") long sid) {
        Student student = mStudentSvc.findById(sid);
        List<Course> taken = student.getCourses();
        List<Course> result = new ArrayList<>();
        List<Course> all = (List<Course>) mCourseSvc.findAll();
        for (Course c : all) {
            if (c.getDependsOn() == null || taken.contains(c.getDependsOn()))
            {
                // Spreci rekurzija
                c.setStudents(null);
                result.add(c);
            }
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/students/")
    public void addStudent(@RequestBody Student student) {
        mStudentSvc.save(student);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "api/students/")
    public void updateStudent(@RequestBody Student student) {
        mStudentSvc.update(student);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "api/students/{id}")
    public void deleteStudent(@PathVariable(value="id") long id) {
        mStudentSvc.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/students/{id}")
    public Student getStudent(@PathVariable(value="id") long id) {
        Student s = mStudentSvc.findById(id);
        fixCourseRecursion(s);
        return s;
    }
}