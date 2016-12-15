SELECT * FROM StudentCourses sc JOIN Students s ON sc.student_id = s.id JOIN Courses c on c.id = sc.course_id;
SELECT * FROM Courses;
SELECT * FROM Students;

SELECT DISTINCT s.* FROM
  Courses c INNER JOIN StudentCourses sc
  ON c.depends_on is null or sc.course_id = c.depends_on
  INNER JOIN Students s ON s.id = sc.student_id
WHERE c.id = 73;
GROUP BY s.id
;