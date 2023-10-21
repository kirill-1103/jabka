begin;

insert into role (id,name)
values
    (100,'ADMIN'),
    (1, 'STUDENT'),
    (2, 'TEACHER'),
    (3, 'CURATOR'),
    (4, 'ENROLLEE'),
    (5, 'MODERATOR'),
    (6, 'COMMITTE');
ON CONFLICT DO NOTHING;

insert into users(id,login,name,surname,email,password)
values
    (100000,'API_ADMIN','API_ADMIN','API_ADMIN','API_ADMIN@admin.admin','$2a$10$WpQf5Ph9ByyQRa4dyvLZS.3hIzjxkb1TbWd4aSLt7rsMtyZdoLoKq'),
    (1, 'STUDENT_1', 'STUDENT_1', 'STUDENT_1', 'STUDENT_1@student.student', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (2, 'STUDENT_2', 'STUDENT_2', 'STUDENT_2', 'STUDENT_2@student.student', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (3, 'STUDENT_3', 'STUDENT_3', 'STUDENT_3', 'STUDENT_3@student.student', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (4, 'STUDENT_4', 'STUDENT_4', 'STUDENT_4', 'STUDENT_4@student.student', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (5, 'STUDENT_5', 'STUDENT_5', 'STUDENT_5', 'STUDENT_5@student.student', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (6, 'TEACHER_1', 'TEACHER_1', 'TEACHER_1', 'TEACHER_1@teacher.teacher', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (7, 'TEACHER_2', 'TEACHER_2', 'TEACHER_2', 'TEACHER_2@teacher.teacher', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO'),
    (8, 'TEACHER_3', 'TEACHER_3', 'TEACHER_3', 'TEACHER_3@teacher.teacher', '$2a$12$LmxAlgfG9z/cuvLo8ZohHOjHNrSI8h5CjiZ8sZ4wKHuTihxCcKwcO');
ON CONFLICT DO NOTHING;

insert into users_roles (user_id, role_id)
    values
       (100000,100),
       (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 2),
       (7, 2),
       (8, 2);
ON CONFLICT DO NOTHING;

commit;