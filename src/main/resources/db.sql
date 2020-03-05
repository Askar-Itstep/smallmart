-- данная БД создавалась из кода по ключу "create" в application.prop
-- параллелно будет приложен файл бэкап БД
CREATE DATABASE `product_db2` /*!40100 DEFAULT CHARACTER SET utf8 */

CREATE TABLE `carts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `is_queue` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5o626f86h46m4s7ms6ginnop` (`user_id`),
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8

CREATE TABLE `items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cart_id` bigint(20) DEFAULT NULL,
  `prod_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKly7dnbs7nn9s3bbs77v89wqvj` (`cart_id`),
  KEY `FK4c6awsgqo5vvsv7j4e5lpq1xy` (`item_id`),
  KEY `FK813gnhs9s59mjvdxqm0vmjnqv` (`prod_id`),
  CONSTRAINT `FK4c6awsgqo5vvsv7j4e5lpq1xy` FOREIGN KEY (`item_id`) REFERENCES `carts` (`id`),
  CONSTRAINT `FK813gnhs9s59mjvdxqm0vmjnqv` FOREIGN KEY (`prod_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKly7dnbs7nn9s3bbs77v89wqvj` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=356 DEFAULT CHARSET=utf8

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `cart_id` bigint(20) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdv26y3bb4vdmsr89c9ppnx85w` (`cart_id`),
  CONSTRAINT `FKdv26y3bb4vdmsr89c9ppnx85w` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8

