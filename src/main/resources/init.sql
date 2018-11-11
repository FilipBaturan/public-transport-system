
INSERT INTO `public_transport`.`station` (`id`, `active`, `coordinates`, `name`) VALUES ('1', TRUE, '56.32', 'Detelinara1');
INSERT INTO `public_transport`.`station` (`id`, `active`, `coordinates`, `name`) VALUES ('2', TRUE, '14.74', 'Detelinara2');
INSERT INTO `public_transport`.`station` (`id`, `active`, `coordinates`, `name`) VALUES ('3', TRUE, '71.61', 'Novo Naselje3');
INSERT INTO `public_transport`.`station` (`id`, `active`, `coordinates`, `name`) VALUES ('4', TRUE, '96.37', 'Novo Naselje5');
INSERT INTO `public_transport`.`station` (`id`, `active`, `coordinates`, `name`) VALUES ('5', TRUE, '47.23', 'Liman1');

INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('1', TRUE, 'Liman-Novo Naselje', '0', '1');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('2', TRUE, 'Liman-Telep', '0', '1');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('3', TRUE, 'Liman-Podbara', '1', '1');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('4', TRUE, 'Detelinara-Telep', '2', '2');
INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`) VALUES ('5', TRUE, 'Detelinara-Podbara', '0', '2');

INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('1', '5');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('1', '2');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('1', '3');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('2', '1');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('2', '5');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('3', '3');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('3', '4');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('3', '5');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('4', '5');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('4', '1');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('5', '3');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('5', '4');
INSERT INTO `public_transport`.`transport_line_stations` (`transport_line_id`, `stations_id`) VALUES ('5', '2');


INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('1', TRUE, 'Liman');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('2', TRUE, 'Detelinara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('3', TRUE, 'Podbara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('4', TRUE, 'Telep');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('5', TRUE, 'Sajmiste');


INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('1', TRUE, 'bus1', '0', '1');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('2', TRUE, 'bus2', '0', '1');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('3', TRUE, 'metro1', '1', '2');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('4', TRUE, 'metro2', '1', '3');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('5', TRUE, 'tram1', '2', '4');
