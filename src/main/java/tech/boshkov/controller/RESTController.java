package tech.boshkov.controller;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tech.boshkov.model.Course;
import tech.boshkov.model.Student;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class RESTController {
        @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET, value = "api/students/")
    public List<Student> students() {
        List<Student> students = this.jdbcTemplate.query("select * from Students", new Student.StudentMapper());

        return students;
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/students/{id}")
    public Student student(@PathVariable(value="id") int id) {
        String sql = "select * from Students WHERE id = ?";
        Student student = this.jdbcTemplate.queryForObject(sql, new Object[]{id},
                new Student.StudentMapper());
        sql = "SELECT course.*, dependant.* FROM StudentCourses AS r INNER JOIN  Courses as course " +
                "ON course.id = r.course_id LEFT JOIN Courses as dependant " +
                "ON course.depends_on = dependant.id WHERE r.student_id = ?";
        List<Course> courses = this.jdbcTemplate.query(sql, new Object[]{id}, new Course.CoursesMapper());
        student.setCourses(courses);
        return student;
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/students/{id}/addToCourse/{cid}")
    public Object addToCourse(@PathVariable(value="id") int id, @PathVariable(value="cid") int cid) {

        //students = this.jdbcTemplate.query("select * from Students", new Student.StudentMapper());
        String sql = "SELECT * FROM Courses as course LEFT JOIN Courses as dependant " +
                "ON course.depends_on = dependant.id WHERE course.id = ?;";
        Course course =  course(cid);
        Student student = student(id);
        List<Course> courses = student.getCourses();
        boolean canAssociate = course.getDependsOn() == null;
        for (Course c : courses) {
            if (c.getId().equals(course.getDependsOn().getId())) {
                canAssociate = true;
                break;
            }
        }

        if (!canAssociate && course.getDependsOn() != null) {
            return String.format("Student with id %d can't be associated with course '%s', because they haven't taken course '%s'",
                    student.getIndex(),
                    course.getName(),
                    course.getDependsOn().getName()
            );
        }

        if (canAssociate) {
            this.jdbcTemplate.update(
                    "INSERT INTO StudentCourses (course_id, student_id) values (?, ?)",
                    course.getId(), student.getId());
            return true;
        }

        return false;
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/students/")
    public int addStudent(@RequestBody Student student) {
        int rows = this.jdbcTemplate.update(
                "INSERT INTO Students(`first_name`, `last_name`, `index`) values (?, ?, ?)",
                student.getFirstName(), student.getLastName(), student.getIndex());

        return rows;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "api/students/{id}")
    public int updateStudent(@RequestBody Student student, @PathVariable(value="id") int id) {

        int rows = this.jdbcTemplate.update(
                "UPDATE Students SET first_name=?, last_name=?, `index`=? WHERE id=?",
                student.getFirstName(), student.getLastName(), student.getIndex(), id);
        return rows;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "api/students/{id}")
    public int deleteStudent(@PathVariable(value="id") int id) {
        int rows = this.jdbcTemplate.update("DELETE FROM Students WHERE id=?", id);
        return rows;
    }


    /*

        Courses

     */

    @RequestMapping(method = RequestMethod.GET, value = "api/courses/")
    public List<Course> courses() {
        List<Course> courses = this.jdbcTemplate.query("SELECT * FROM Courses as course LEFT JOIN Courses as dependant ON course.depends_on = dependant.id;", new Course.CoursesMapper());

        return courses;
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/courses/")
    public int addCourse(@RequestBody Course course) {
        //List<Course> courses = this.jdbcTemplate.query("SELECT * FROM Courses as course LEFT JOIN Courses as dependant ON course.depends_on = dependant.id;", new Course.CoursesMapper());
        Integer dependsOn = null;
        if (course.getDependsOn() != null) {
            dependsOn = course.getDependsOn().getId();
        }

        int rows = this.jdbcTemplate.update(
                "INSERT INTO Courses(name, depends_on) values (?, ?)",
                course.getName(), dependsOn);

        return rows;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "api/courses/{id}")
    public int updateCourse(@RequestBody Course course, @PathVariable(value="id") int id) {
        Integer dependsOn = null;
        if (course.getDependsOn() != null) {
            dependsOn = course.getDependsOn().getId();
        }

        int rows = this.jdbcTemplate.update(
                "UPDATE Courses SET name=?, depends_on=? WHERE id=?",
                course.getName(), dependsOn, id);
        return rows;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "api/courses/{id}")
    public int deleteCourse(@PathVariable(value="id") int id) {
        int rows = this.jdbcTemplate.update("DELETE FROM Courses WHERE id=?", id);
        return rows;
    }


    @RequestMapping(method = RequestMethod.GET, value = "api/courses/{id}")
    public Course course(@PathVariable(value="id") int id) {
        String sql = "SELECT * FROM Courses as course LEFT JOIN Courses as dependant " +
                "ON course.depends_on = dependant.id WHERE course.id = ?;";
        Course course = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, new Course.CoursesMapper());
        sql = "SELECT student.* FROM StudentCourses AS r INNER JOIN Students AS student ON student.id = r.student_id WHERE r.course_id = ? GROUP BY student.id;";
        List<Student> students = this.jdbcTemplate.query(sql, new Object[]{id}, new Student.StudentMapper());
        course.setStudents(students);
        return course;
    }
}