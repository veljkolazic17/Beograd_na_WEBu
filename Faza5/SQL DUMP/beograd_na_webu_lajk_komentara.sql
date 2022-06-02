-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: db-mysql-fra1-62868-do-user-11453844-0.b.db.ondigitalocean.com    Database: beograd_na_webu
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'c4292114-c585-11ec-a178-363bd632e22a:1-9802';

--
-- Table structure for table `lajk_komentara`
--

DROP TABLE IF EXISTS `lajk_komentara`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lajk_komentara` (
  `idkomentar` int NOT NULL,
  `idkorisnik` int NOT NULL,
  PRIMARY KEY (`idkomentar`,`idkorisnik`),
  KEY `idkorisnikj_lajk2_fk_idx` (`idkorisnik`),
  CONSTRAINT `idkomentar_lajk2_fk` FOREIGN KEY (`idkomentar`) REFERENCES `komentar` (`idkomentar`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idkorisnikj_lajk2_fk` FOREIGN KEY (`idkorisnik`) REFERENCES `korisnik` (`idkorisnik`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lajk_komentara`
--

LOCK TABLES `lajk_komentara` WRITE;
/*!40000 ALTER TABLE `lajk_komentara` DISABLE KEYS */;
INSERT INTO `lajk_komentara` VALUES (83,25),(85,25),(86,25),(87,25),(88,25),(80,54),(81,54),(82,54),(82,57),(85,57),(86,57),(88,57),(91,57);
/*!40000 ALTER TABLE `lajk_komentara` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-02 22:13:45
