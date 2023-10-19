begin;

insert into role (id,name)
values(100,'ADMIN')
ON CONFLICT DO NOTHING;

insert into users(id,login,name,surname,email,password)
values(100000,'API_ADMIN','API_ADMIN','API_ADMIN','API_ADMIN@admin.admin','$2a$10$WpQf5Ph9ByyQRa4dyvLZS.3hIzjxkb1TbWd4aSLt7rsMtyZdoLoKq')
ON CONFLICT DO NOTHING;

insert into users_roles (user_id, role_id)
    values(100000,100)
ON CONFLICT DO NOTHING;

commit;