package tech.boshkov.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tech.boshkov.model.Course;
import tech.boshkov.model.Student;

import java.util.List;

/**
 * Created by user on 12/14/16.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {

}
