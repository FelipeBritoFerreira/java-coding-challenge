CREATE TABLE IF NOT EXISTS `product` (
  `id_product` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `brand` VARCHAR(200) NOT NULL,
  `stock` INT(10) DEFAULT 0,
  `fk_seller` INT(10) unsigned NOT NULL,
  `uuid` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id_product`),
  UNIQUE KEY `uuid` (`uuid`),
  CONSTRAINT fk_seller FOREIGN KEY (fk_seller) REFERENCES seller(id_seller)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;