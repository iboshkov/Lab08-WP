package tech.boshkov.service;

import tech.boshkov.model.Course;

/**
 * Created by user on 12/12/16.
 */
public interface CourseService {
    Iterable<Course> findAll();
    Course findById(Long id);
    void save(Course course);
    void update(Course course);
    void delete(Long id);
}
