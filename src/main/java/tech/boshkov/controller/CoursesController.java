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
public class CoursesController {
    @Autowired
    private CourseService mCourseSvc;
    @Autowired
    private StudentService mStudentSvc;

    public CoursesController() {
    }

    protected void fixStudentRecursion(Course c) {
        for (Student s : c.getStudents()) {
            s.setCourses(null);
        }
    }

    protected void fixStudentRecursion(List<Student> l) {
        for (Student s : l) {
            s.setCourses(null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/courses/")
    public List<Course> getCourses() {
        // Spreci rekurzija
        List<Course> courses = (List<Course>) mCourseSvc.findAll();
        for (Course c : courses) {
            fixStudentRecursion(c);
        }
        return courses;
    }


    @RequestMapping(method = RequestMethod.GET, value = "api/courses/{cid}/qualified")
    public List<Student> getQualifiedStudents(@PathVariable(value="cid") long cid) {
        Course c = mCourseSvc.findById(cid);
        if (c.getDependsOn() == null ) {
            List<Student> l = (List<Student>) mStudentSvc.findAll();
            fixStudentRecursion(l);
            return l;
        }
        Course dependant = c.getDependsOn();
        fixStudentRecursion(dependant);
        return dependant.getStudents();
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/courses/")
    public void addCourse(@RequestBody Course course) {
        mCourseSvc.save(course);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "api/courses/")
    public void updateCourse(@RequestBody Course course) {
        mCourseSvc.update(course);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "api/courses/{id}")
    public void updateCourse(@RequestBody Course course, @PathVariable(value = "id") long id) {
        mCourseSvc.update(course);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "api/courses/{id}")
    public void deleteCourse(@PathVariable(value="id") long id) {
        mCourseSvc.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/courses/{id}")
    public Course getCourse(@PathVariable(value="id") long id) {
        Course c = mCourseSvc.findById(id);
        fixStudentRecursion(c);
        if (c.getDependsOn() != null)
            fixStudentRecursion(c.getDependsOn());
        return c;
    }
}