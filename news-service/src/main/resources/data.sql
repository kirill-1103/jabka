begin;

— Запрос 1
insert into news(header, creation_date, text, hashtags)
values('Открытие новой учебной программы', '2023-10-16 13:00:00',
'Университет рад представить новую программу обучения, которая поможет студентам развить свои навыки и получить актуальные знания в своей области.',
ARRAY['новости', 'учебная программа', 'студенты']) ON CONFLICT DO NOTHING;

— Запрос 2
insert into news(header, creation_date, text, hashtags)
values('Интерактивная лекция по истории искусства', '2023-10-14 13:00:00',
'Пригласаем всех студентов на интерактивную лекцию по истории искусства. Будем изучать великих художников и их произведения!',
ARRAY['лекция', 'искусство', 'студенты']) ON CONFLICT DO NOTHING;

— Запрос 3
insert into news(header, creation_date, text, hashtags)
values('Выставка проектов студентов', '2023-10-10 13:00:00',
'Сегодня открывается выставка проектов, созданных нашими талантливыми студентами. Приходите и удивляйтесь их креативности!',
ARRAY['выставка', 'студенты', 'творчество']) ON CONFLICT DO NOTHING;

— Запрос 4
insert into news(header, creation_date, text, hashtags)
values('День открытых дверей', '2023-10-05 13:00:00',
'Завтра у нас День открытых дверей. Приходите с друзьями и узнайте больше о возможностях, которые предоставляет университет.',
ARRAY['день открытых дверей', 'просмотр', 'студенты']) ON CONFLICT DO NOTHING;

— Запрос 5
insert into news(header, creation_date, text, hashtags)
values('Студенческая конференция по инженерии', '2023-09-30 13:00:00',
'Студенческая конференция по инженерии состоится через неделю. Регистрируйтесь и делитесь своими исследованиями с коллегами!',
ARRAY['конференция', 'инженерия', 'студенты']) ON CONFLICT DO NOTHING;
— Запрос 6
insert into news(header, creation_date, text, hashtags)
values('Спортивное событие: баскетбольный турнир', '2023-09-25 13:00:00',
'Баскетбольный турнир среди студентов пройдет на следующей неделе. Подготовьтесь и докажите свою спортивную выносливость!',
ARRAY['спорт', 'баскетбол', 'турнир']) ON CONFLICT DO NOTHING;

— Запрос 7
insert into news(header, creation_date, text, hashtags)
values('Встреча с известным писателем', '2023-09-20 13:00:00',
'Известный писатель проведет лекцию и встречу с членами университетской общественности. Не упустите шанс пообщаться с литературной звездой!',
ARRAY['писатель', 'лекция', 'литература']) ON CONFLICT DO NOTHING;

— Запрос 8
insert into news(header, creation_date, text, hashtags)
values('Новые исследования в области науки о данных', '2023-09-15 13:00:00',
'Наши исследователи продолжают работу над проектами в области науки о данных. Ожидайте новых открытий и публикаций!',
ARRAY['исследования', 'наука о данных', 'студенты']);

— Запрос 9
insert into news(header, creation_date, text, hashtags)
values('Семинар по предпринимательству', '2023-09-10 13:00:00',
'У нас будет семинар по предпринимательству, где вы сможете узнать больше о запуске своего собственного бизнеса. Приходите и воплощайте свои идеи в жизнь!',
ARRAY['семинар', 'предпринимательство', 'бизнес']) ON CONFLICT DO NOTHING;

— Запрос 10
insert into news(header, creation_date, text, hashtags)
values('Подготовка к выпускным экзаменам', '2023-09-05 13:00:00',
'Студенты, начните подготовку к выпускным экзаменам заранее. Наши преподаватели готовы помочь вам достичь успеха!',
ARRAY['выпускные экзамены', 'подготовка', 'студенты']) ON CONFLICT DO NOTHING;

— Запрос 11
insert into news(header, creation_date, text, hashtags)
values('Стипендии для талантливых студентов', '2023-08-31 13:00:00',
'Университет предоставляет стипендии для талантливых студентов. Заявки принимаются до 15 сентября. Не упустите свой шанс на обучение!',
ARRAY['стипендии', 'талант', 'студенты']) ON CONFLICT DO NOTHING;

— Запрос 12
insert into news(header, creation_date, text, hashtags)
values('Зимний международный фестиваль искусств', '2023-08-25 13:00:00',
'Зимний фестиваль искусств приглашает участников со всего мира. Подготовьтесь к незабываемым выступлениям и выставкам!',
ARRAY['фестиваль', 'искусство', 'международное событие']) ON CONFLICT DO NOTHING;

--
Запрос 13
insert into news(header, creation_date, text, hashtags)
values('Завершение летней школы для абитуриентов', '2023-08-20 13:00:00',
'Летняя школа для абитуриентов завершается церемонией награждения. Молодые ученики получили новые знания и дружбы.',
ARRAY['летняя школа', 'абитуриенты', 'образование']) ON CONFLICT DO NOTHING;

— Запрос 14
insert into news(header, creation_date, text, hashtags)
values('Волонтёрская акция по уборке территории', '2023-08-15 13:00:00',
'Приглашаем всех студентов присоединиться к волонтёрской акции по уборке территории университета. Вместе мы сделаем мир чище!',
ARRAY['волонтёры', 'уборка', 'территория']) ON CONFLICT DO NOTHING;
commit;