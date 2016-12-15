package tech.boshkov.service;

import tech.boshkov.model.Course;
import tech.boshkov.model.Student;

/**
 * Created by user on 12/15/16.
 */
public interface StudentService {
    Iterable<Student> findAll();
    Student findById(Long id);
    void save(Student student);
    void update(Student student);
    void delete(Long id);
}
