-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 3.36.85.4    Database: S101
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `name` enum('PRIVATE_DATA_CREATE','PRIVATE_DATA_READ','PRIVATE_DATA_UPDATE','PRIVATE_DATA_DELETE','ROLE_CREATE','ROLE_UPDATE','ROLE_DELETE','TEAM_UPDATE','TEAM_DELETE','TEAM_INVITE','TEAM_REMOVE','PRIVATE_DATA_ROLE_UPDATE') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invitation`
--

DROP TABLE IF EXISTS `invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `expired_at` datetime(6) DEFAULT NULL,
  `invited_member_email` varchar(255) DEFAULT NULL,
  `organization_role` enum('HEADER','ADMIN','MEMBER') DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKns52wrt9rpwep29mtt4d2igcl` (`member_id`),
  KEY `FK8rgqw88fuolsn290hpuybefcn` (`organization_id`),
  KEY `FKbhli2pgnvw1v309xy639rwdcu` (`role_id`),
  KEY `FKabkuebobnaedj8wl2lqdrtjhd` (`team_id`),
  CONSTRAINT `FK8rgqw88fuolsn290hpuybefcn` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `FKabkuebobnaedj8wl2lqdrtjhd` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FKbhli2pgnvw1v309xy639rwdcu` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKns52wrt9rpwep29mtt4d2igcl` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `encrypted_totp_data_key` binary(48) DEFAULT NULL,
  `encrypted_totp_iv` binary(32) DEFAULT NULL,
  `encrypted_totp_key` binary(48) DEFAULT NULL,
  `expired_at` datetime(6) DEFAULT NULL,
  `master_key_version` bigint DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `second_login_cnt` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member_organization`
--

DROP TABLE IF EXISTS `member_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_organization` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `organization_role` enum('HEADER','ADMIN','MEMBER') DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7ag6nv5ndk7f1iuuqm0k92gql` (`member_id`),
  KEY `FKicvg4mm670ju3gkpjpx2bw81n` (`organization_id`),
  CONSTRAINT `FK7ag6nv5ndk7f1iuuqm0k92gql` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKicvg4mm670ju3gkpjpx2bw81n` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member_role`
--

DROP TABLE IF EXISTS `member_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK34g7epqlcxqloewku3aoqhhmg` (`member_id`),
  KEY `FKdiix07v86r3ntrbs3l02qr7y0` (`role_id`),
  KEY `FKo0dksqbjqgutgapkh6m2b6wuw` (`team_id`),
  CONSTRAINT `FK34g7epqlcxqloewku3aoqhhmg` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKdiix07v86r3ntrbs3l02qr7y0` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKo0dksqbjqgutgapkh6m2b6wuw` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member_team`
--

DROP TABLE IF EXISTS `member_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_team` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgqbjnbjtn5tycg5ih6r80wmr1` (`member_id`),
  KEY `FKfly863tmgmm8wnj0u1sqgqu5u` (`team_id`),
  CONSTRAINT `FKfly863tmgmm8wnj0u1sqgqu5u` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FKgqbjnbjtn5tycg5ih6r80wmr1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `action_url` varchar(255) DEFAULT NULL,
  `from_member` bigint DEFAULT NULL,
  `from_member_nick_name` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `status` enum('READ','UNREAD') DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `to_member` bigint DEFAULT NULL,
  `to_member_nick_name` varchar(255) DEFAULT NULL,
  `type` enum('SYSTEM','USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `team_count` int DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaenr3b6apid0s8v330rpn7ntn` (`member_id`),
  CONSTRAINT `FKaenr3b6apid0s8v330rpn7ntn` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `organization_dashboard`
--

DROP TABLE IF EXISTS `organization_dashboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization_dashboard` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `count` int NOT NULL,
  `month` tinyint DEFAULT NULL,
  `rotate` int NOT NULL,
  `views` int NOT NULL,
  `year` smallint DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkdqwkhwtj1adm7mkmg4ka1tpm` (`organization_id`),
  CONSTRAINT `FKkdqwkhwtj1adm7mkmg4ka1tpm` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `private_data`
--

DROP TABLE IF EXISTS `private_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `content` blob,
  `count` int DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `private_data_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` enum('LOGIN','TEXT') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKetk879xmnrr2p0gw8hejl7a6j` (`team_id`),
  CONSTRAINT `FKetk879xmnrr2p0gw8hejl7a6j` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `private_data_role`
--

DROP TABLE IF EXISTS `private_data_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_data_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `privatedata_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhkd0nud1bahe4lg53c6hhq4fe` (`privatedata_id`),
  KEY `FK8kf01c6u9osw27nm7b84adqby` (`role_id`),
  CONSTRAINT `FK8kf01c6u9osw27nm7b84adqby` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKhkd0nud1bahe4lg53c6hhq4fe` FOREIGN KEY (`privatedata_id`) REFERENCES `private_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=350 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7a0lhymjedsmnitpglgavt2wb` (`team_id`),
  CONSTRAINT `FK7a0lhymjedsmnitpglgavt2wb` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS `role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `authority_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqbri833f7xop13bvdje3xxtnw` (`authority_id`),
  KEY `FK2052966dco7y9f97s1a824bj1` (`role_id`),
  CONSTRAINT `FK2052966dco7y9f97s1a824bj1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKqbri833f7xop13bvdje3xxtnw` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1042 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `encrypted_data_key` binary(48) DEFAULT NULL,
  `encrypted_iv` binary(32) DEFAULT NULL,
  `expired_at` datetime(6) DEFAULT NULL,
  `master_key_version` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role_count` int DEFAULT NULL,
  `secret_count` int DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdyddawruqdwvuy3n6kgmc4q4j` (`member_id`),
  KEY `FKt2rwhhxcjdmje0gqqybiyjdpn` (`organization_id`),
  CONSTRAINT `FKdyddawruqdwvuy3n6kgmc4q4j` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKt2rwhhxcjdmje0gqqybiyjdpn` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team_dashboard`
--

DROP TABLE IF EXISTS `team_dashboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_dashboard` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `count` int NOT NULL,
  `month` tinyint DEFAULT NULL,
  `rotate` int NOT NULL,
  `views` int NOT NULL,
  `year` smallint DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKih9shh0wy0rcx7sp2w2p3vgai` (`team_id`),
  CONSTRAINT `FKih9shh0wy0rcx7sp2w2p3vgai` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-21 13:06:51
