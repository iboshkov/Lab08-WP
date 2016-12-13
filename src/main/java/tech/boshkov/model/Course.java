package tech.boshkov.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 12/12/16.
 */
public class Course {
    private Integer id;
    private String name;
    private Course depends_on;
    private List<Student> students;

    public static class CoursesMapper implements RowMapper<Course> {
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setName(rs.getString("course.name"));
            //course.setDependsOn(rs.getInt("course.depends_on"));
            course.setId(rs.getInt("course.id"));
            try {
                Integer dependant_id = rs.getInt("dependant.id");
                if (!rs.wasNull()) {
                    Course dependant = new Course();
                    dependant.setId(rs.getInt("dependant.id"));
                    dependant.setName(rs.getString("dependant.name"));
                    course.setDependsOn(dependant);
                }
            } catch (SQLException ignored) {

            }

            return course;
        }
    }


    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
