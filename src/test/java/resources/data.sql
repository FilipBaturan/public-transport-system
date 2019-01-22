INSERT INTO `station` (`id`, `active`, `name`, `type`) VALUES ('1', TRUE, 'Liman1', '0');
INSERT INTO `station` (`id`, `active`, `name`, `type`) VALUES ('2', TRUE, 'Detelinara1', '0');


INSERT INTO `station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('1', TRUE, '45.24398', '19.82504', '1');
INSERT INTO `station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('2', TRUE, '45.24565', '19.84045', '2');

INSERT INTO `zone` (`id`, `active`, `name`) VALUES ('1', TRUE, 'Novi Sad');
INSERT INTO `zone` (`id`, `active`, `name`) VALUES ('2', TRUE, 'Liman');
INSERT INTO `zone` (`id`, `active`, `name`) VALUES ('3', TRUE, 'Detelinara');
INSERT INTO `zone` (`id`, `active`, `name`) VALUES ('4', TRUE, 'Podbara');
INSERT INTO `zone` (`id`, `active`, `name`) VALUES ('5', TRUE, 'Telep');
INSERT INTO `zone` (`id`, `active`, `name`) VALUES ('6', TRUE, 'Sajmiste');


INSERT INTO `transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('1', TRUE, 'R1', '0', '2');
INSERT INTO `transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('2', TRUE, 'R2', '0', '2');
INSERT INTO `transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('3', TRUE, 'R3', '0', '2');
INSERT INTO `transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('4', TRUE, 'R4', '0', '2');


INSERT INTO `transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('1', TRUE, '45.26377,19.82895 45.26407,19.82122 45.26274,19.81878 45.26015,19.82195 45.25761,19.82431 45.2529,19.82431 45.24867,19.82466 45.24398,19.82504 45.23972,19.82552 45.23712,19.82655 45.23879,19.83264 45.24166,19.84268 45.24565,19.84045 45.24951,19.83839 45.25229,19.83685 45.25833,19.83341 45.26154,19.83174 45.26419,19.83028 45.26377,19.82891 (red|R1)', '1');
INSERT INTO `transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('2', TRUE, '45.25374,19.78947 45.24939,19.79187 45.24552,19.79419 45.24208,19.79642 45.24166,19.80234 45.24111,19.80809 45.24057,19.81333 45.24009,19.81822 45.23966,19.8232 45.24311,19.82054 45.24649,19.81813 45.25,19.81556 45.25411,19.81273 45.25755,19.81041 45.26033,19.80852 45.25731,19.80054 45.25507,19.7935 45.25368,19.78955(green,w7|R2)', '2');
INSERT INTO `transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('3', TRUE, '45.25314,19.80432 45.24728,19.80766 45.24836,19.81504 45.24927,19.82054 45.24921,19.83144 45.25151,19.8359 45.25404,19.84234 45.25616,19.84131 45.26063,19.84294 45.26274,19.83968 45.26111,19.83358 45.25954,19.82766 45.25894,19.82105 45.25628,19.81359 45.25495,19.80904 45.2532,19.80449(brown|R3)', '3');


INSERT INTO `vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('1', TRUE, 'bus1', '0', '1');
INSERT INTO `vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('2', TRUE, 'bus2', '0', '1');


INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('100', TRUE, 1, 0);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('101', TRUE, 2, 0);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('102', TRUE, 3, 0);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('103', TRUE, 1, 1);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('104', TRUE, 2, 1);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('105', TRUE, 1, 2);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES ('106', TRUE, 2, 2);


INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('100', '08:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('100', '08:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('100', '08:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('100', '08:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('100', '09:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('101', '10:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('101', '10:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('101', '10:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('101', '10:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('101', '11:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('102', '12:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('102', '12:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('102', '12:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('102', '12:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('102', '13:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('103', '18:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('103', '18:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('103', '18:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('103', '18:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('103', '19:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('104', '15:10');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('104', '15:25');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('104', '15:40');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('104', '15:55');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('104', '16:05');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('105', '22:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('105', '22:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('105', '22:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('105', '22:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('105', '23:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('106', '23:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('106', '23:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('106', '23:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('106', '23:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES ('106', '00:00');

INSERT INTO `user` (`type`, `id`, `active`, `authority`, `confirmation`, `email`, `last_name`, `first_name`, `password`, `telephone`, `username`, `balance`, `document`) VALUES ('REGISTERED_USER', '1', true, '0', '2', 'email@gmail.com', 'LastName', 'FirstName', 'password', '00213123', 'username', '100', 'asds');
INSERT INTO `user` (`type`, `id`, `active`, `authority`, `email`, `last_name`, `first_name`, `password`, `username`) VALUES ('VALIDATOR', '2', true, '2', 'email@gmail.com', 'ValLastName', 'ValFirstName', 'valPassword', 'valUsername');
INSERT INTO `user` (`type`, `id`, `active`, `authority`, `email`, `last_name`, `first_name`, `password`, `username`) VALUES ('OPERATOR', '3', true, '3', 'null@gmail.com', 'David', 'Davidovic', '$2a$04$CpdJcjORftUKRbJpynFIa.qUY2c/DoqNmta2dL.o6Wkw1I3ZLh79C', 'null');
INSERT INTO `user` (`type`, `id`, `active`, `authority`, `email`, `last_name`, `first_name`, `password`, `username`) VALUES ('OPERATOR', '4', true, '3', 'mail@mail.com', 'Ime', 'Prezime', '$2a$04$mEe6vqwMvGidyoQ8K.w8E.dLMXs12a6j6GKHrQFW6POXFhk027CuC', 'operkor');


INSERT INTO `item` (`id`, `active`, `age`, `cost`, `type`, `zone_id`, `vehicle_type`) VALUES ('1', true, '0', '150', '1', '1', '0');
INSERT INTO `pricelist` (`id`, `active`, `end_date`, `start_date`) VALUES ('1', true, '2020-01-22', '2010-01-22');
INSERT INTO `pricelist_item` (`id`, `active`, `item_id`, `pricelist_id`) VALUES ('1', true, '1', '1');
INSERT INTO `reservation` (`id`, `active`, `owner_id`) VALUES ('1', true, '1');

INSERT INTO `ticket` (`id`, `active`, `expiry_date`, `purchase_date`, `token`, `line_id`, `price_list_item_id`, `reservation_id`) VALUES ('1', true, '2018-04-24 07:39:16', '2016-12-20 00:00:00', 'qweqwe', '1', '1', '1');
INSERT INTO `ticket` (`id`, `active`, `expiry_date`, `purchase_date`, `token`, `line_id`, `price_list_item_id`, `reservation_id`) VALUES ('2', true, '2018-04-24 07:39:16', '2016-12-20 00:00:00', 'zxczxczxc', '1', '1', '1');






ALTER sequence `hibernate_sequence` restart WITH 500