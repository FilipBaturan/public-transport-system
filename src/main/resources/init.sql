INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('1', TRUE, 'Liman1', '0');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`, `type`) VALUES ('2', TRUE, 'Detelinara1', '0');


INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('1', TRUE, '45.24398', '19.82504', '1');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('2', TRUE, '45.24565', '19.84045', '2');


INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('1', TRUE, 'Liman');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('2', TRUE, 'Detelinara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('3', TRUE, 'Podbara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('4', TRUE, 'Telep');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('5', TRUE, 'Sajmiste');


INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('1', TRUE, 'R1', '0', '1');


INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `content`, `transport_line_id`) VALUES ('1', TRUE, '45.26377,19.82895 45.26407,19.82122 45.26274,19.81878 45.26015,19.82195 45.25761,19.82431 45.2529,19.82431 45.24867,19.82466 45.24398,19.82504 45.23972,19.82552 45.23712,19.82655 45.23879,19.83264 45.24166,19.84268 45.24565,19.84045 45.24951,19.83839 45.25229,19.83685 45.25833,19.83341 45.26154,19.83174 45.26419,19.83028 45.26377,19.82891 (red|R1)', '1');


INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('1', TRUE, 'bus1', '0', '1');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('2', TRUE, 'bus2', '0', '1');
