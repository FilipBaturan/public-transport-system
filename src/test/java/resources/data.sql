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


INSERT INTO `transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('1', TRUE, '45.26377,19.82895 45.26407,19.82122 45.26274,19.81878 45.26015,19.82195 45.25761,19.82431 45.2529,19.82431 45.24867,19.82466 45.24398,19.82504 45.23972,19.82552 45.23712,19.82655 45.23879,19.83264 45.24166,19.84268 45.24565,19.84045 45.24951,19.83839 45.25229,19.83685 45.25833,19.83341 45.26154,19.83174 45.26419,19.83028 45.26377,19.82891 (red|R1)', '1');
INSERT INTO `transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('2', TRUE, '45.26377,19.82895 45.26407,19.82122 45.26274,19.81878 45.26015,19.82195 45.25761,19.82431 45.2529,19.82431 45.24867,19.82466 45.24398,19.82504 45.23972,19.82552 45.23712,19.82655 45.23879,19.83264 45.24166,19.84268 45.24565,19.84045 45.24951,19.83839 45.25229,19.83685 45.25833,19.83341 45.26154,19.83174 45.26419,19.83028 45.26377,19.82891 (red|R2)', '2');
INSERT INTO `transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('3', TRUE, '45.26377,19.82895 45.26407,19.82122 45.26274,19.81878 45.26015,19.82195 45.25761,19.82431 45.2529,19.82431 45.24867,19.82466 45.24398,19.82504 45.23972,19.82552 45.23712,19.82655 45.23879,19.83264 45.24166,19.84268 45.24565,19.84045 45.24951,19.83839 45.25229,19.83685 45.25833,19.83341 45.26154,19.83174 45.26419,19.83028 45.26377,19.82891 (red|R3)', '3');


INSERT INTO `vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('1', TRUE, 'bus1', '0', '1');
INSERT INTO `vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('2', TRUE, 'bus2', '0', '1');


INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (100, TRUE, 1, 0);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (101, TRUE, 2, 0);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (102, TRUE, 3, 0);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (103, TRUE, 1, 1);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (104, TRUE, 2, 1);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (105, TRUE, 1, 2);
INSERT INTO `schedule` (`id`, `active`, `transport_line_id`, `day_of_week`) VALUES (106, TRUE, 2, 2);


INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (100, '08:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (100, '09:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (101, '10:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (101, '11:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (102, '12:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (102, '13:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (103, '18:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (103, '19:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:10');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:25');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:40');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (104, '15:55');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (104, '16:05');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (105, '22:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (105, '23:00');

INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:00');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:15');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:30');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (106, '23:45');
INSERT INTO `schedule_departures` (`schedule_id`, `departure`) VALUES (106, '00:00');

alter sequence `hibernate_sequence` restart with 500