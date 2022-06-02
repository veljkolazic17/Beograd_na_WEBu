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
-- Table structure for table `komentar`
--

DROP TABLE IF EXISTS `komentar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `komentar` (
  `idkomentar` int NOT NULL AUTO_INCREMENT,
  `tekst_komentara` varchar(281) NOT NULL,
  `idkorisnik` int DEFAULT NULL,
  `idsmestaj` int NOT NULL,
  `broj_lajkova` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`idkomentar`),
  KEY `idkorisnik_komentar_fk_idx` (`idkorisnik`),
  KEY `idsmestaj_komentar_fk_idx` (`idsmestaj`),
  CONSTRAINT `idkorisnik_komentar_fk` FOREIGN KEY (`idkorisnik`) REFERENCES `korisnik` (`idkorisnik`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idsmestaj_komentar_fk` FOREIGN KEY (`idsmestaj`) REFERENCES `smestaj` (`idsmestaj`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `komentar`
--

LOCK TABLES `komentar` WRITE;
/*!40000 ALTER TABLE `komentar` DISABLE KEYS */;
INSERT INTO `komentar` VALUES (80,'Jako ruzan stan',25,337,1),(81,'Slazem se sa Veljkom!',54,337,1),(82,'Ovaj stan je jako kul',54,341,2),(83,'Hmmmm nije los',54,344,1),(84,'Ovaj zuti zid je jako ruzan, ostalo je super lepo xD',54,346,0),(85,'Ovo mi se svidja!',54,339,2),(86,'Ja sam ADMIN i sada cu ti obrisati komentar kmetu!',25,339,2),(87,'I ovaj komentar cu ti obrisati hehe!',25,344,1),(88,'Ovo je moj prvi komentar! SRECAN SAM :))))))',57,339,2),(89,'Ovo je moj drugi komentar. JAKO SAM SRECAN :))))))))))))))))))))',57,341,0),(90,'Volim ETF :))))))))))))))))))))))))))))))',57,345,0),(91,'IMAM JAKO SPOKOJAN I SRECAN ZIVOT :---))))))))))))))))))))))))))))))))))))))',57,340,1);
/*!40000 ALTER TABLE `komentar` ENABLE KEYS */;
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

-- Dump completed on 2022-06-02 22:13:48
