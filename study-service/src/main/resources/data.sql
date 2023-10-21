begin;

INSERT INTO attendance (student_id, schedule_id, attendance_status)
VALUES
    (1, 1, 'ATTENDED'),
    (1, 3, 'ATTENDED'),
    (2, 1, 'MISSED'),
    (2, 3, 'ATTENDED'),
    (3, 2, 'ATTENDED'),
    (3, 1, 'MISSED'),
    (4, 2, 'ATTENDED'),
    (4, 3, 'ATTENDED'),
    (5, 2, 'MISSED'),
    (5, 3, 'MISSED');

INSERT INTO homework (student_id, date, grade, comment, study_materials_id)
VALUES
    (1, '2023-10-21 08:00:00', 90, 'Good work!', 1),
    (1, '2023-10-23 08:00:00', 92, 'Excellent!', 2),
    (1, '2023-10-23 08:00:00', 65, 'Not Bad.', 3),
    (2, '2023-10-22 08:00:00', 89, 'Well done!', 1),
    (2, '2023-10-22 08:00:00', 87, 'Well done!', 2),
    (2, '2023-10-22 08:00:00', 85, 'Well done!', 3),
    (3, '2023-10-23 08:00:00', 92, 'Excellent!', 3),
    (3, '2023-10-23 08:00:00', 69, 'Not Bad.', 1),
    (3, '2023-10-23 08:00:00', 87, 'Excellent!', 2);

INSERT INTO professor_id (professor_id)
VALUES
    (6),
    (7),
    (8);

INSERT INTO schedule (date_time, group_id, subject_id, class_format, auditorium, link_for_the_class, professor_id)
VALUES
    ('2023-10-23 13:00:00', 1, 1, 'IN_PERSON', 'Room A101', 'https://example.com/class1', 101),
    ('2023-10-24 14:00:00', 2, 2, 'DISTANT', 'Online', 'https://example.com/class2', 102),
    ('2023-10-25 15:00:00', 3, 3, 'IN_PERSON', 'Room B202', 'https://example.com/class3', 103);

INSERT INTO study_groups (name)
VALUES
    ('Group A'),
    ('Group B'),
    ('Group C');

INSERT INTO study_materials (type, subject_id, materials_text)
VALUES
    ('MATERIAL', 1, 'Lecture notes for subject A'),
    ('TASK', 2, 'Homework assignment for subject B'),
    ('MATERIAL', 3, 'Study materials for subject C');

INSERT INTO subject (name, creator_id)
VALUES
    ('Subject A', 5),
    ('Subject B', 6),
    ('Subject C', 7);
