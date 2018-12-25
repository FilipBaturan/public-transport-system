INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('1', TRUE, 'Liman1', '0');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('2', TRUE, 'Detelinara1', '0');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('3', TRUE, 'Sajmiste1', '2');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('4', TRUE, 'Grbavica1', '2');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('5', TRUE, 'Glavna', '0');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('6', TRUE, 'Centar1', '0');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('7', TRUE, 'Futog1', '0');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('8', TRUE, 'Futog3', '1');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('9', TRUE, 'Glavna', '1');


INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('1', TRUE, '45.24398', '19.82504', '1');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('2', TRUE, '45.24565', '19.84045', '2');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('3', TRUE, '45.25211122444926', '19.789466857910156', '3');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('4', TRUE, '45.25017762921895', '19.814443588256836', '4');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('6', TRUE, '45.25374264422109', '19.84268188476562', '6');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('7', TRUE, '45.248666962141556', '19.83109474182129', '7');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('8', TRUE, '45.25966370001255', '19.809293746948242', '8');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('9', TRUE, '45.26371141342837', '19.8317813873291', '9');


INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('1', TRUE, 'Novi Sad');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('2', TRUE, 'Liman');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('3', TRUE, 'Detelinara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('4', TRUE, 'Podbara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('5', TRUE, 'Telep');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('6', TRUE, 'Sajmiste');


INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('1', TRUE, 'R1', '0', '2');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('2', TRUE, 'G7', '2', '2');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('3', TRUE, 'V3', '0', '3');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('4', TRUE, 'M5', '0', '4');

INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('1', TRUE, '45.26377,19.82895 45.26407,19.82122 45.26274,19.81878 45.26015,19.82195 45.25761,19.82431 45.2529,19.82431 45.24867,19.82466 45.24398,19.82504 45.23972,19.82552 45.23712,19.82655 45.23879,19.83264 45.24166,19.84268 45.24565,19.84045 45.24951,19.83839 45.25229,19.83685 45.25833,19.83341 45.26154,19.83174 45.26419,19.83028 45.26377,19.82891 (red|R1)', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('2', TRUE, '45.25374,19.78947 45.24939,19.79187 45.24552,19.79419 45.24208,19.79642 45.24166,19.80234 45.24111,19.80809 45.24057,19.81333 45.24009,19.81822 45.23966,19.8232 45.24311,19.82054 45.24649,19.81813 45.25,19.81556 45.25411,19.81273 45.25755,19.81041 45.26033,19.80852 45.25731,19.80054 45.25507,19.7935 45.25368,19.78955(green,w7|G7)', '2');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('3', TRUE, '45.25314,19.80432 45.24728,19.80766 45.24836,19.81504 45.24927,19.82054 45.24921,19.83144 45.25151,19.8359 45.25404,19.84234 45.25616,19.84131 45.26063,19.84294 45.26274,19.83968 45.26111,19.83358 45.25954,19.82766 45.25894,19.82105 45.25628,19.81359 45.25495,19.80904 45.2532,19.80449(brown|V3)', '3');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('4', TRUE, ' 45.26398,19.83019 45.26543,19.84401 45.25417,19.84208 45.24891,19.81668 45.26039,19.80904 45.26395,19.83028(purple,w10|M5)', '4');


INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('1', TRUE, 'bus1', '0', '1');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('2', TRUE, 'bus2', '0', '1');


INSERT INTO `public_transport`.`item` (`id`, `active`, `age`, `cost`, `type`, `zone_id`, `vehicle_type`) VALUES ('1', TRUE, '0', '150', '1', '1', '0');
INSERT INTO `public_transport`.`pricelist` (`id`, `active`, `end_date`, `start_date`) VALUES ('1', TRUE, '2020-01-22', '2010-01-22');
INSERT INTO `public_transport`.`pricelist_item` (`id`, `active`, `item_id`, `pricelist_id`) VALUES ('1', TRUE, '1', '1');
INSERT INTO `public_transport`.`reservation` (`id`, `active`, `owner_id`) VALUES ('1', TRUE, '1');
INSERT INTO `public_transport`.`ticket` (`id`, `active`, `expiry_date`, `purchase_date`, `token`, `line_id`, `price_list_item_id`, `reservation_id`) VALUES ('1', TRUE, '2018-02-01', '2018-03-01', 'qweqwe', '1', '1', '1');

INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (100, TRUE, 1, 0);
INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (101, TRUE, 2, 0);
INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (102, TRUE, 3, 0);
INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (103, TRUE, 1, 1);
INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (104, TRUE, 2, 1);
INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (105, TRUE, 1, 2);
INSERT INTO `public_transport`.`schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (106, TRUE, 2, 2);


INSERT INTO `public_transport`.`user` (`type`, `id`, `active`, `authority`, `confirmation`, `email`, `last_name`, `name`, `password`, `telephone`, `username`, `balance`, `document`) VALUES ('REGISTERED_USER', '1', TRUE, '0', '2', 'asdasd.asd@gmail.com', 'LastName', 'FirstName', 'pass', '0120120012', 'username1', '100', 'asds');
INSERT INTO `public_transport`.`user` (`type`, `id`, `active`, `authority`, `email`, `last_name`, `name`, `password`, `username`, `balance`, `document`) VALUES ('VALIDATOR', '2', TRUE, '2', 'asdasd.asd@gmail.com', '', 'nekoIme', '123123', 'username2', '100', 'asds');
INSERT INTO `public_transport`.`user` (`type`, `id`, `active`, `authority`, `email`, `last_name`, `name`, `password`, `username`) VALUES ('VALIDATOR', '4', TRUE, '2', 'newEmail', '', 'newName', 'newPass', 'newUserName');


INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:00');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:15');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:30');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:45');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (100, '09:00');


INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:00');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:15');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:30');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:45');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (101, '11:00');

INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:00');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:15');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:30');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:45');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (102, '13:00');

INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:00');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:15');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:30');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:45');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (103, '19:00');

INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:10');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:25');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:40');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:55');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (104, '16:05');

INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:00');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:15');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:30');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:45');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (105, '23:00');

INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:00');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:15');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:30');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:45');
INSERT INTO `public_transport`.`schedule_departures` (`schedule_id`, `departure`) VALUES (106, '00:00');

SET SQL_SAFE_UPDATES = 0;
UPDATE `public_transport`.`hibernate_sequence` SET `next_val` = '200' WHERE `next_val` = '1';
