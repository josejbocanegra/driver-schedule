-----------------------
--Delete data ---------
-----------------------

delete from schedule_entity;
delete from driver_entity;

-----------------------
--Insert drivers ------
-----------------------

insert into driver_entity (id, name) values (1, 'Juan Alvarez');
insert into driver_entity (id, name) values (2, 'María Rodríguez');
insert into driver_entity (id, name) values (3, 'Mafe Gómez');
insert into driver_entity (id, name) values (4, 'Carlos Parra');
insert into driver_entity (id, name) values (5, 'Lucas Vega');
insert into driver_entity (id, name) values (6, 'Ana Galindo');
insert into driver_entity (id, name) values (7, 'Juan Hernandez');
insert into driver_entity (id, name) values (8, 'Lucía Mora');
insert into driver_entity (id, name) values (9, 'Elvia García');
insert into driver_entity (id, name) values (10, 'Gustavo Cuellar');
insert into driver_entity (id, name) values (11, 'Rosa Bejarano');
insert into driver_entity (id, name) values (12, 'Lida Trujillo');
insert into driver_entity (id, name) values (13, 'Angela Silva');
insert into driver_entity (id, name) values (14, 'Franklin Gutierrez');
insert into driver_entity (id, name) values (15, 'Lina Toro');
insert into driver_entity (id, name) values (16, 'Andrea Serna');
insert into driver_entity (id, name) values (17, 'Luis Trujillo');
insert into driver_entity (id, name) values (18, 'Elena Marquez');
insert into driver_entity (id, name) values (19, 'Sergio Sierra');
insert into driver_entity (id, name) values (20, 'Nancy Pérez');

-----------------------
--Insert schedule -----
-----------------------

insert into schedule_entity(id, date, is_available, driver_id) values (1, '2022-03-29 08:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (2, '2022-03-29 09:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (3, '2022-03-29 10:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (4, '2022-03-29 11:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (5, '2022-03-29 12:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (6, '2022-03-29 13:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (7, '2022-03-29 14:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (8, '2022-03-29 15:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (9, '2022-03-29 16:00', true, 1);
insert into schedule_entity(id, date, is_available, driver_id) values (10, '2022-03-29 17:00', true, 1);

insert into schedule_entity(id, date, is_available, driver_id) values (11, '2022-03-30 09:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (12, '2022-03-30 09:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (13, '2022-03-30 10:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (14, '2022-03-30 11:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (15, '2022-03-30 12:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (16, '2022-03-30 13:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (17, '2022-03-30 14:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (18, '2022-03-30 15:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (19, '2022-03-30 16:00', true, 2);
insert into schedule_entity(id, date, is_available, driver_id) values (20, '2022-03-30 17:00', true, 2);

insert into schedule_entity(id, date, is_available, driver_id) values (21, '2022-04-01 08:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (22, '2022-04-01 09:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (23, '2022-04-01 10:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (24, '2022-04-01 11:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (25, '2022-04-01 12:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (26, '2022-04-01 13:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (27, '2022-04-01 14:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (28, '2022-04-01 15:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (29, '2022-04-01 16:00', true, 3);
insert into schedule_entity(id, date, is_available, driver_id) values (30, '2022-04-01 17:00', true, 3);

insert into schedule_entity(id, date, is_available, driver_id) values (31, '2022-04-02 08:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (32, '2022-04-02 09:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (33, '2022-04-02 10:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (34, '2022-04-02 11:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (35, '2022-04-02 12:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (36, '2022-04-02 13:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (37, '2022-04-02 14:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (38, '2022-04-02 15:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (39, '2022-04-02 16:00', true, 4);
insert into schedule_entity(id, date, is_available, driver_id) values (40, '2022-04-02 17:00', true, 4);

insert into schedule_entity(id, date, is_available, driver_id) values (41, '2022-04-03 08:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (42, '2022-04-03 09:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (43, '2022-04-03 10:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (44, '2022-04-03 11:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (45, '2022-04-03 12:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (46, '2022-04-03 13:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (47, '2022-04-03 14:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (48, '2022-04-03 15:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (49, '2022-04-03 16:00', true, 5);
insert into schedule_entity(id, date, is_available, driver_id) values (50, '2022-04-03 17:00', true, 5);


