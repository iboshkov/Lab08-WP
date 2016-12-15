package tech.boshkov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.boshkov.model.Course;
import tech.boshkov.model.Student;
import tech.boshkov.persistence.StudentRepository;

import java.util.List;

/**
 * Created by user on 12/15/16.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository mStudentRepo;

    @Override
    public List<Student> findAll() {
        return (List<Student>) mStudentRepo.findAll();
    }

    @Override
    public Student findById(Long id) {
        return mStudentRepo.findOne(id);
    }

    @Override
    public void save(Student student) {
        mStudentRepo.save(student);
    }

    @Override
    public void update(Student student) {
        save(student);
    }

    @Override
    public void delete(Long id) {
        mStudentRepo.delete(id);
    }
}
