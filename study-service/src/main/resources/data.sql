begin;

INSERT INTO professor_id (professor_id)
VALUES
    (1),
    (2),
    (3)
ON CONFLICT DO NOTHING;

INSERT INTO study_groups (id, name)
VALUES
    (1,'Group A'),
    (2,'Group B'),
    (3,'Group C')
ON CONFLICT DO NOTHING;



INSERT INTO subject (id, name, creator_id)
VALUES
    (1,'Subject A', 5),
    (2,'Subject B', 6),
    (3,'Subject C', 7)
ON CONFLICT DO NOTHING;


INSERT INTO schedule (id, date_time, group_id, subject_id, class_format, auditorium, link_for_the_class, professor_professor_id)
VALUES
    (1,'2023-10-23 13:00:00', 1, 1, 'IN_PERSON', 'Room A101', 'https://example.com/class1', 1),
    (2,'2023-10-24 14:00:00', 2, 2, 'DISTANT', 'Online', 'https://example.com/class2', 2),
    (3,'2023-10-25 15:00:00', 3, 3, 'IN_PERSON', 'Room B202', 'https://example.com/class3', 3),
    (4,'2023-10-25 15:00:00', 3, 3, 'IN_PERSON', 'Room B202', 'https://example.com/class3', 1)
ON CONFLICT DO NOTHING;


INSERT INTO attendance ( id,student_id, schedule_id, attendance_status)
VALUES
    (1,1, 1, 'ATTENDED'),
    (2,1, 3, 'ATTENDED'),
    (3,2, 1, 'MISSED'),
    (4,2, 3, 'ATTENDED'),
    (5,3, 2, 'ATTENDED'),
    (6,3, 1, 'MISSED'),
    (7,4, 2, 'ATTENDED'),
    (8,4, 3, 'ATTENDED'),
    (9,5, 2, 'MISSED'),
    (10,5, 3, 'MISSED')
ON CONFLICT DO NOTHING;

INSERT INTO study_materials (id, type, subject_id, materials_text)
VALUES
    (1,'MATERIAL', 1, 'Lecture notes for subject A'),
    (2,'TASK', 2, 'Homework assignment for subject B'),
    (3,'MATERIAL', 3, 'Study materials for subject C')
ON CONFLICT DO NOTHING;

INSERT INTO homework (id, student_id, date, grade, comment, task_id)
VALUES
    (1,1, '2023-10-21 08:00:00', 90, 'Good work!', 1),
    (2,1, '2023-10-23 08:00:00', 92, 'Excellent!', 2),
    (3,1, '2023-10-23 08:00:00', 65, 'Not Bad.', 3),
    (4,2, '2023-10-22 08:00:00', 89, 'Well done!', 1),
    (5,2, '2023-10-22 08:00:00', 87, 'Well done!', 2),
    (6,2, '2023-10-22 08:00:00', 85, 'Well done!', 3),
    (7,3, '2023-10-23 08:00:00', 92, 'Excellent!', 3),
    (8,3, '2023-10-23 08:00:00', 69, 'Not Bad.', 1),
    (9,3, '2023-10-23 08:00:00', 87, 'Excellent!', 2)
ON CONFLICT DO NOTHING;

end;
