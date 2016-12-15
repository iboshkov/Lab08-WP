package tech.boshkov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.boshkov.model.Course;
import tech.boshkov.model.Student;
import tech.boshkov.persistence.CourseRepository;
import tech.boshkov.persistence.StudentRepository;

@SpringBootApplication
public class Lab08Application {
	private static final Logger log = LoggerFactory.getLogger(Lab08Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Lab08Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(CourseRepository crepo, StudentRepository srepo) {
		return (args) -> {
			crepo.deleteAll();
			srepo.deleteAll();

			Student ilija = new Student("Ilija", "Boshkov", 131223);
			Student aleksandar = new Student("Aleksandar", "Andonov", 131333);
			Student tome = new Student("Tome", "Tomev", 133013);
			Student kocka = new Student("Ilija", "Kocev", 134013);

			srepo.save(ilija);
			srepo.save(aleksandar);
			srepo.save(tome);
			srepo.save(kocka);

			// save a couple of customers
			Course strukturno = crepo.save(new Course("Strukturno", null));

			strukturno.addStudent(ilija);
			strukturno.addStudent(kocka);
			strukturno.addStudent(aleksandar);
			crepo.save(strukturno);

			Course objektno = crepo.save(new Course("Objektno", strukturno));
			objektno.addStudent(ilija);
			crepo.save(objektno);
			Course mrezi = crepo.save(new Course("Mrezi", null));
			mrezi.addStudent(ilija);
			crepo.save(mrezi);

			strukturno = crepo.findOne(strukturno.getId());
			log.info("Course found with findOne():");
			log.info(strukturno.toString());
			log.info("Students on that course:");
			for (Student s : strukturno.getStudents()) {
				log.info(s.toString());
				for (Course c : s.getCourses()) {
					log.info("Who has taken " + c.toString());
				}
			}
		};
	}

}
