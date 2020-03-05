CREATE DATABASE  IF NOT EXISTS `product_db2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `product_db2`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: product_db2
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `is_queue` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5o626f86h46m4s7ms6ginnop` (`user_id`),
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (69,6800,90,'2019-12-11 13:59:52','\0'),(70,3500,91,'2019-12-11 14:43:18','\0'),(71,14800,92,'2019-12-11 15:35:13','\0'),(72,7000,93,'2019-12-12 05:12:17','\0'),(73,7200,94,'2019-12-12 11:01:31','\0'),(74,8000,95,'2019-12-12 11:05:33','\0'),(75,6400,96,'2019-12-12 11:09:04','\0'),(76,7100,97,'2019-12-12 11:18:49','\0'),(77,7200,98,'2019-12-12 11:34:41','\0'),(78,6200,99,'2019-12-12 13:50:46','\0'),(79,9400,100,'2019-12-12 13:59:02','\0'),(80,5700,101,'2019-12-12 14:01:53','\0'),(81,7100,102,'2019-12-12 14:06:34','\0'),(82,5700,103,'2019-12-12 14:07:50','\0'),(87,2000,105,'2019-12-12 14:14:47','\0'),(92,3200,110,'2019-12-12 15:37:05','\0'),(93,3500,111,'2019-12-12 15:43:10','\0'),(97,14200,113,'2019-12-12 15:46:14','\0'),(98,10700,114,'2019-12-12 15:47:52','\0'),(105,13700,120,'2019-12-12 15:56:17','\0'),(106,11500,121,'2019-12-12 16:00:13','\0'),(107,9900,122,'2019-12-12 16:03:49','\0'),(108,3500,123,'2019-12-12 16:05:39','\0'),(111,3200,126,'2019-12-12 16:07:57','\0'),(114,3200,129,'2019-12-12 16:15:11','\0'),(116,11200,130,'2019-12-12 16:16:32','\0'),(117,7200,131,'2019-12-12 16:18:49','\0'),(118,14300,132,'2019-12-12 16:19:04','\0'),(119,10300,133,'2019-12-12 16:31:40','\0'),(120,14400,134,'2019-12-12 16:33:18','\0'),(121,7900,135,'2019-12-13 02:03:48','\0'),(122,4200,136,'2019-12-13 02:05:25','\0'),(124,7900,137,'2019-12-13 02:06:05','\0'),(125,10700,138,'2019-12-13 02:07:24','\0'),(126,11000,139,'2019-12-13 02:20:03','\0'),(127,10700,140,'2019-12-13 02:21:45','\0'),(128,10700,141,'2019-12-13 02:30:09','\0'),(129,7200,142,'2019-12-13 02:56:39','\0'),(130,7500,143,'2019-12-13 02:58:00','\0'),(131,7500,144,'2019-12-13 02:59:00','\0'),(132,7500,145,'2019-12-13 03:02:11','\0'),(133,7500,146,'2019-12-13 03:03:21','\0'),(134,7500,147,'2019-12-13 03:04:40','\0'),(135,7500,148,'2019-12-13 03:09:05','\0'),(136,7500,149,'2019-12-13 03:36:38','\0'),(137,7500,150,'2019-12-13 03:39:43','\0'),(138,7500,151,'2019-12-13 03:56:46','\0'),(139,10700,152,'2019-12-13 04:00:07','\0'),(140,7500,153,'2019-12-13 04:03:06','\0'),(141,8000,154,'2019-12-13 04:10:47','\0'),(142,7500,157,'2019-12-13 04:30:17','\0'),(143,11000,158,'2019-12-13 04:43:01','\0'),(144,7200,159,'2019-12-13 04:43:23','\0'),(145,4200,160,'2019-12-13 05:10:41','\0'),(146,4000,161,'2019-12-13 05:12:18','\0'),(147,4000,162,'2019-12-13 05:14:52','\0'),(148,3500,163,'2019-12-13 05:18:01','\0'),(149,4000,164,'2019-12-13 05:29:21','\0'),(150,7500,165,'2019-12-13 05:29:39','\0'),(151,4000,166,'2019-12-13 05:31:08','\0'),(152,4000,167,'2019-12-13 05:40:38','\0'),(153,3600,168,'2019-12-13 05:41:49','\0'),(154,4000,169,'2019-12-13 05:45:39',''),(155,7500,170,'2019-12-13 06:08:53',''),(156,6800,171,'2019-12-13 06:10:56',''),(157,4400,172,'2019-12-13 14:02:27','\0'),(158,6400,173,'2019-12-13 14:07:38','\0'),(159,6400,174,'2019-12-13 14:17:15','\0'),(160,7000,175,'2019-12-13 14:21:31','\0'),(161,7000,176,'2019-12-13 14:23:25','\0'),(162,7000,177,'2019-12-13 14:26:50','\0'),(163,7200,179,'2019-12-13 14:31:11','\0'),(164,5600,180,'2019-12-13 14:34:22',''),(165,7500,181,'2019-12-13 14:38:26','\0'),(166,9400,182,'2019-12-13 14:50:19','\0'),(167,10800,183,'2019-12-13 14:55:16',''),(168,9000,184,'2019-12-13 14:56:23','');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=356 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (97,69,4,69),(99,69,2,69),(101,70,1,70),(102,71,2,71),(103,71,4,71),(104,71,5,71),(105,71,5,71),(106,72,1,72),(107,72,1,72),(108,73,2,73),(109,73,2,73),(110,74,5,74),(111,74,5,74),(112,75,4,75),(113,75,4,75),(114,76,1,76),(115,76,2,76),(116,77,4,77),(117,77,5,77),(118,78,8,78),(119,78,8,78),(120,78,9,78),(121,79,7,79),(122,79,7,79),(123,79,8,79),(124,80,7,80),(125,80,8,80),(126,81,1,81),(127,81,2,81),(128,82,7,82),(129,82,8,82),(147,87,8,87),(158,92,4,92),(159,93,6,93),(169,97,1,97),(170,97,1,97),(171,97,2,97),(172,97,2,97),(173,98,1,98),(174,98,2,98),(175,98,2,98),(195,105,5,105),(196,105,5,105),(197,105,6,105),(198,NULL,9,NULL),(199,NULL,9,NULL),(200,NULL,9,NULL),(201,105,9,105),(202,106,5,106),(203,106,5,106),(204,106,6,106),(205,107,8,107),(206,107,8,107),(207,107,9,107),(208,NULL,7,NULL),(209,NULL,7,NULL),(210,NULL,7,NULL),(211,107,7,107),(212,108,1,108),(217,111,4,111),(222,114,4,114),(226,116,4,116),(227,116,2,116),(228,116,9,116),(229,116,9,116),(230,117,2,117),(231,117,2,117),(232,118,2,118),(233,118,1,118),(234,118,4,118),(235,118,5,118),(236,119,1,119),(237,119,2,119),(238,119,4,119),(239,120,2,120),(240,120,2,120),(241,120,4,120),(242,120,5,120),(243,121,8,121),(244,121,9,121),(245,121,7,121),(246,122,8,122),(247,122,9,122),(254,124,7,124),(255,124,8,124),(256,124,9,124),(257,125,4,125),(258,125,5,125),(259,125,6,125),(260,126,5,126),(261,126,6,126),(262,126,6,126),(263,127,4,127),(264,127,5,127),(265,127,6,127),(266,128,4,128),(267,128,5,128),(268,128,6,128),(269,129,4,129),(270,129,5,129),(271,130,5,130),(272,130,6,130),(273,131,5,131),(274,131,6,131),(275,132,5,132),(276,132,6,132),(277,133,5,133),(278,133,6,133),(279,134,5,134),(280,134,6,134),(281,135,5,135),(282,135,6,135),(283,136,5,136),(284,136,6,136),(285,137,5,137),(286,137,6,137),(287,138,5,138),(288,138,6,138),(289,139,4,139),(290,139,5,139),(291,139,6,139),(292,140,5,140),(293,140,6,140),(298,141,5,141),(299,141,5,141),(302,142,5,142),(304,142,6,142),(306,143,5,143),(307,143,6,143),(308,143,6,143),(309,144,4,144),(310,144,5,144),(312,145,8,145),(313,145,9,145),(314,146,5,146),(315,147,5,147),(316,148,6,148),(317,149,5,149),(318,150,5,150),(319,150,6,150),(320,151,5,151),(321,152,5,152),(322,153,2,153),(323,154,5,154),(324,155,5,155),(325,155,6,155),(326,156,2,156),(327,156,4,156),(328,157,9,157),(329,157,9,157),(330,158,4,158),(331,158,4,158),(332,159,4,159),(333,159,4,159),(334,160,1,160),(335,160,1,160),(336,161,1,161),(337,161,1,161),(338,162,1,162),(339,162,1,162),(340,163,2,163),(341,163,2,163),(342,164,2,164),(343,164,8,164),(344,165,5,165),(345,165,6,165),(346,166,7,166),(347,166,7,166),(348,166,8,166),(349,167,2,167),(350,167,2,167),(351,167,2,167),(352,168,2,168),(354,168,4,168),(355,168,9,168);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'964b553b-a9af-4869-8985-2f43bb06c150. terminator.png',3500,'DVD Terminator'),(2,'2f2f8c70-26c3-46e9-ad0e-12f5af95c66d. terminator2.png',3600,'DVD Terminator 2'),(4,'ffb0012b-daaa-4de0-bb46-a8a4f65e24e0. commando.png',3200,'DVD Commando'),(5,'22b2a44f-1457-4435-8b9d-7c398e8ea446. matrix.png',4000,'DVD Matrix'),(6,'5741612c-9ccc-44d3-8f55-3b1d04ccd5a8. predator.png',3500,'Predator'),(7,'706d5d2e-c6b0-4080-afd4-6c35c1498b49. rocky4.png',3700,'Rokki4'),(8,'f1ed56cd-9413-47d1-aff8-b5478cac9c5d. default.png',2000,'Buggs Bunny'),(9,'faaf2fee-b6c8-4cb9-8de8-11fece7947b4. titanic.png',2200,'Titanic');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'USER'),(1,'ADMIN'),(2,'USER'),(2,'MANAGER'),(2,'EMPLOYEE'),(90,'USER'),(173,'USER'),(177,'USER'),(179,'USER'),(180,'USER'),(181,'USER'),(182,'USER'),(183,'USER'),(184,'USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'cap','cap',NULL,NULL,NULL),(2,'user','user',NULL,NULL,NULL),(90,'','client',69,NULL,NULL),(91,'','client',70,NULL,NULL),(92,'','client',71,NULL,NULL),(93,'email@example.com','Alex',72,'87023695245','Karaganda, Erubaeva st., 23-56'),(94,'email@example.com','Alex',73,'87023695245','Karaganda, Erubaeva st., 23-56'),(95,'bob@example.com','Bob',74,'87023695246','Karaganda, Erubaeva st., 23-57'),(96,'sid@example.com','Sid',75,'87023695247','Karaganda, Erubaeva st., 23-58'),(97,'dino@example.com','Dino',76,'87023695249','Karaganda, Erubaeva st., 23-60'),(98,'elena@example','Elena',77,'87023695250','Karaganda, Erubaeva st., 23-50'),(99,'flora@example.com','Flora',78,'87023695255','Karaganda, Erubaeva st., 42-62'),(100,'juno@example.com','Juno',79,'87023695256','Karaganda, Erubaeva st., 11-22'),(101,'','client',80,NULL,NULL),(102,'email@example.com','Alex',81,'87023695245','Karaganda, Erubaeva st., 23-56'),(103,'email@example.com','Alexandra',82,'87023695245','Karaganda, Erubaeva st., 23-56'),(105,'alexo@example.com','Alexo',87,'87023695275','Karaganda, Erubaeva st., 22-22'),(110,NULL,'client',92,NULL,NULL),(111,NULL,'client',93,NULL,NULL),(113,NULL,'client',97,NULL,NULL),(114,NULL,'client',98,NULL,NULL),(120,'email@example.com','Alex',105,'87023695245','Karaganda, Erubaeva st., 23-56'),(121,'email@example.com','Alex',106,'87023695245','Karaganda, Erubaeva st., 23-56'),(122,'email@example.com','Alex',107,'87023695245','Karaganda, Erubaeva st., 23-56'),(123,NULL,'client',108,NULL,NULL),(126,NULL,'client',111,NULL,NULL),(129,NULL,'client',114,NULL,NULL),(130,'email@example.com','Alex',116,'87023695245','Karaganda, Erubaeva st., 23-56'),(131,'email@example.com','Alex',117,'87023695245','Karaganda, Erubaeva st., 23-56'),(132,NULL,'client',118,NULL,NULL),(133,NULL,'client',119,NULL,NULL),(134,NULL,'client',120,NULL,NULL),(135,'email@example.com','Alex',121,'87023695245','Karaganda, Erubaeva st., 23-56'),(136,'email@example.com','Alex',122,'87023695245','Karaganda, Erubaeva st., 23-56'),(137,NULL,'client',124,NULL,NULL),(138,NULL,'client',125,NULL,NULL),(139,NULL,'client',126,NULL,NULL),(140,NULL,'client',127,NULL,NULL),(141,NULL,'client',128,NULL,NULL),(142,NULL,'client',129,NULL,NULL),(143,NULL,'client',130,NULL,NULL),(144,NULL,'client',131,NULL,NULL),(145,NULL,'client',132,NULL,NULL),(146,NULL,'client',133,NULL,NULL),(147,NULL,'client',134,NULL,NULL),(148,NULL,'client',135,NULL,NULL),(149,NULL,'client',136,NULL,NULL),(150,NULL,'client',137,NULL,NULL),(151,NULL,'client',138,NULL,NULL),(152,NULL,'client',139,NULL,NULL),(153,NULL,'client',140,NULL,NULL),(154,NULL,'client',141,NULL,NULL),(155,NULL,'client',141,NULL,NULL),(156,NULL,'client',141,NULL,NULL),(157,'email@example.com','Alex',142,'87023695245','Karaganda, Erubaeva st., 23-56'),(158,'email@example.com','Alex',143,'87023695245','Karaganda, Erubaeva st., 23-56'),(159,'email@example.com','Alex',144,'87023695245','Karaganda, Erubaeva st., 23-56'),(160,'email@example.com','Alex',145,'87023695245','Karaganda, Erubaeva st., 23-56'),(161,'email@example.com','Alex',146,'87023695245','Karaganda, Erubaeva st., 23-56'),(162,'email@example.com','Alex',147,'87023695245','Karaganda, Erubaeva st., 23-56'),(163,'email@example.com','Alex',148,'87023695245','Karaganda, Erubaeva st., 23-56'),(164,'email@example.com','Alex',149,'87023695245','Karaganda, Erubaeva st., 23-56'),(165,'email@example.com','Alex',150,'87023695245','Karaganda, Erubaeva st., 23-56'),(166,'email@example.com','Alex',151,'87023695245','Karaganda, Erubaeva st., 23-56'),(167,'email@example.com','Alex',152,'87023695245','Karaganda, Erubaeva st., 23-56'),(168,'email@example.com','Alex',153,'87023695245','Karaganda, Erubaeva st., 23-56'),(169,'email@example.com','Alex',154,'87023695245','Karaganda, Erubaeva st., 23-56'),(170,'email@example.com','Alex',155,'87023695245','Karaganda, Erubaeva st., 23-56'),(171,'email@example.com','Alex',156,'87023695245','Karaganda, Erubaeva st., 23-56'),(172,NULL,'client',157,NULL,NULL),(173,NULL,'client',158,NULL,NULL),(174,NULL,'client',159,NULL,NULL),(175,NULL,'client',160,NULL,NULL),(176,NULL,'client',161,NULL,NULL),(177,NULL,'client',162,NULL,NULL),(179,'email@example.com','Alex',163,'87023695245','Karaganda, Erubaeva st., 23-56'),(180,'email@example.com','Alex',164,'87023695245','Karaganda, Erubaeva st., 23-56'),(181,'hugo@example.com','Hugo',165,'87023696040','Karaganda, Erubaeva st., 60-40'),(182,'zenf@example.com','Zena',166,'87023695050','Karaganda, Erubaeva st., 50-50'),(183,'zita@example.com','Zita',167,'87023697777','Karaganda, Erubaeva st., 77-77'),(184,'zital@example.com','Zita',168,'87023697777','Karaganda, Erubaeva st., 77-77');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-13 21:08:56
