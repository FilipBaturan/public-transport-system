INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('1', TRUE, '45.25693', '26.32475', '1');
INSERT INTO `public_transport`.`station_position` (`id`, `active`, `latitude`, `longitude`, `station_id`) VALUES ('2', TRUE, '46.14785', '26.12596', '2');

INSERT INTO `public_transport`.`station` (`id`, `active`, `name`) VALUES ('1', TRUE, 'Liman1');
INSERT INTO `public_transport`.`station` (`id`, `active`, `name`) VALUES ('2', TRUE, 'Detelinara1');

INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('1', TRUE, '45.26377', '19.82895', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('2', TRUE, '45.26407', '19.82122', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('3', TRUE, '45.26274', '19.81878', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('4', TRUE, '45.26015', '19.82195', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('5', TRUE, '45.25761', '19.82431', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('6', TRUE, '45.2529', '19.82431', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('7', TRUE, '45.24867', '19.82466', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('8', TRUE, '45.24398', '19.82504', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('9', TRUE, '45.23972', '19.82552', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('10', TRUE, '45.23712', '19.82655', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('11', TRUE, '45.23879', '19.83264', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('12', TRUE, '45.24166', '19.84268', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('13', TRUE, '45.24565', '19.84045', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('14', TRUE, '45.24951', '19.83839', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('15', TRUE, '45.25229', '19.83685', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('16', TRUE, '45.25833', '19.83341', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('17', TRUE, '45.26154', '19.83174', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('18', TRUE, '45.26419', '19.83028', '1');
INSERT INTO `public_transport`.`transport_line_position` (`id`, `active`, `latitude`, `longitude`, `transport_line_id`) VALUES ('19', TRUE, '45.26377', '19.82891', '1');

INSERT INTO `public_transport`.`transport_line` (`id`, `active`, `name`, `type`, `zone_id`, `color`, `width`) VALUES ('1', TRUE, 'R1', '0', '1', 'red', 'w10');


INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('1', TRUE, 'Liman');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('2', TRUE, 'Detelinara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('3', TRUE, 'Podbara');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('4', TRUE, 'Telep');
INSERT INTO `public_transport`.`zone` (`id`, `active`, `name`) VALUES ('5', TRUE, 'Sajmiste');


INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('1', TRUE, 'bus1', '0', '1');
INSERT INTO `public_transport`.`vehicle` (`id`, `active`, `name`, `type`, `current_line_id`) VALUES ('2', TRUE, 'bus2', '0', '1');
