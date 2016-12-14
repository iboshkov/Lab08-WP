SELECT s.* FROM
  Courses c INNER JOIN StudentCourses sc
  ON c.depends_on is null or sc.course_id = c.depends_on
  INNER JOIN Students s ON s.id = sc.student_id
WHERE c.id = 3
GROUP BY s.id
;