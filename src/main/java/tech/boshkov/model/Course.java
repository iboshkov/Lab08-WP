package tech.boshkov.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 12/12/16.
 */
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @OneToOne
    @JoinColumn(name = "depends_on")
    private Course depends_on;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="StudentCourses",
            joinColumns=@JoinColumn(name="course_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="student_id", referencedColumnName="id"))
    private List<Student> students;


    public Course() {
        this.students = new ArrayList<>();
    }

    public Course(String _name, Course _depends) {
        name = _name;
        depends_on = _depends;
        this.students = new ArrayList<>();
    }


    public void addStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }

    @Override
    public String toString() {
        String out = "Course: " + this.name;
        if (this.depends_on != null)
            out += "\tDepends on " + depends_on;
        return out;
    }

    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> s) {
        students = s;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getDependsOn() {
        return depends_on;
    }

    public void setDependsOn(Course depends_on) {
        this.depends_on = depends_on;
    }
}
