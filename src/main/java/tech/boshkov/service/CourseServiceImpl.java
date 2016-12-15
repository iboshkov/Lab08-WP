package tech.boshkov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.boshkov.model.Course;
import tech.boshkov.persistence.CourseRepository;

import java.util.List;

/**
 * Created by user on 12/15/16.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository mCourseRepo;

    @Override
    public List<Course> findAll() {
        return (List<Course>) mCourseRepo.findAll();
    }

    @Override
    public Course findById(Long id) {
        return mCourseRepo.findOne(id);
    }

    @Override
    public void save(Course course) {
        mCourseRepo.save(course);
    }

    @Override
    public void update(Course course) {
        save(course);
    }

    @Override
    public void delete(Long id) {
        mCourseRepo.delete(id);
    }
}
