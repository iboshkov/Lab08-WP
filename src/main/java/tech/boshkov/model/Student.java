package tech.boshkov.model;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/12/16.
 */
@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer personal_id;
    private String first_name;
    private String last_name;

    /*@ManyToMany(targetEntity = Course.class, mappedBy = "personal_id")
    @JoinTable(name = "StudentCourses")
    private List<CourseAssociation> courses;
*/
    @ManyToMany(mappedBy="students", fetch = FetchType.EAGER)
    private List<Course> courses;

    public Student() {
        this.courses = new ArrayList<>();
    }

    public Student(String firstName, String lastName, int _index) {
        first_name = firstName;
        last_name = lastName;
        personal_id = _index;
        this.courses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("[%d] %s %s", personal_id, first_name, last_name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(Integer personal_id) {
        this.personal_id = personal_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }


    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> c) {
        courses = c;
    }

}
