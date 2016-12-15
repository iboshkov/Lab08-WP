SELECT * FROM Courses;

DROP TABLE IF EXISTS StudentCourses;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Students;

CREATE TABLE Students (
  `id` INTEGER not null AUTO_INCREMENT PRIMARY KEY,
  `first_name` text,
  `last_name` text,
  `personal_id` INTEGER UNIQUE
);

CREATE TABLE Courses (
  `id` INTEGER not null AUTO_INCREMENT PRIMARY KEY,
  `name` text,
  `depends_on` INTEGER DEFAULT NULL,
  FOREIGN KEY (`depends_on`) REFERENCES Courses(id)
  ON DELETE CASCADE
);

CREATE TABLE StudentCourses (
  `student_id` INTEGER not null,
  `course_id` INTEGER not null,

  FOREIGN KEY (`student_id`)
  REFERENCES Students(id)
  ON DELETE CASCADE,
  FOREIGN KEY (`course_id`)
  REFERENCES Courses(id)
  ON DELETE CASCADE,
  PRIMARY KEY (student_id, course_id)
);



INSERT INTO Courses (`name`, depends_on)  VALUES ("Strukturno Programiranje", NULL);
INSERT INTO Courses (`name`, depends_on)  VALUES ("Algoritmi i Podatocni Strukturi", 1);

INSERT INTO Students (first_name, last_name, `personal_id`)  VALUES ("Ilija", "Boshkov", 131223);
INSERT INTO Students (first_name, last_name, `personal_id`)  VALUES ("Lazar", "Nikolov", 131087);
INSERT INTO Students (first_name, last_name, `personal_id`)  VALUES ("Tome", "Tomev", 131333);

INSERT INTO StudentCourses (student_id, course_id)  VALUES (1, 1);
INSERT INTO StudentCourses (student_id, course_id)  VALUES (1, 2);
INSERT INTO StudentCourses (student_id, course_id)  VALUES (2, 1);
SELECT student.* FROM StudentCourses as r INNER JOIN Students as student ON student.id = r.student_id WHERE r.course_id = 2 GROUP BY student.id;
SELECT course.*, dependant.* FROM StudentCourses AS r INNER JOIN  Courses as course ON course.id = r.course_id LEFT JOIN Courses as dependant ON course.depends_on = dependant.id WHERE r.student_id = 1