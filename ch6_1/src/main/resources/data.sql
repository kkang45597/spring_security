INSERT IGNORE INTO `sbdt_db`.`user` (`id`, `username`, `password`, `algorithm`)
	VALUES (1, 'john', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT IGNORE INTO `sbdt_db`.`authority` (`id`, `name`, `user`)
	VALUES (1, 'READ', 1),
	       (2, 'WRITE', 1);

INSERT IGNORE INTO `sbdt_db`.`product` (`id`, `name`, `price`, `currency`)
	VALUES (1, 'Chocolate', 10, 'USD');
