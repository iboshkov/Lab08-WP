package tech.boshkov.persistence;

import org.springframework.data.repository.CrudRepository;
import tech.boshkov.model.Course;

/**
 * Created by user on 12/14/16.
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
}
