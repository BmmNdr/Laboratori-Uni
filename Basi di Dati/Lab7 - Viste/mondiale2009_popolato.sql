CREATE DATABASE  IF NOT EXISTS `mondiale2009_popolato` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mondiale2009_popolato`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: mondiale20092016
-- ------------------------------------------------------
-- Server version	5.6.10

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
-- Table structure for table `Appartiene`
--

DROP TABLE IF EXISTS `Appartiene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Appartiene` (
  `Pilota` int(11) NOT NULL,
  `Squadra` int(11) NOT NULL,
  PRIMARY KEY (`Pilota`,`Squadra`),
  KEY `fk_squadra2_idx` (`Squadra`),
  CONSTRAINT `fk_pilota2` FOREIGN KEY (`Pilota`) REFERENCES `Pilota` (`CodPilota`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_squadra2` FOREIGN KEY (`Squadra`) REFERENCES `Squadra` (`idSQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Appartiene`
--

LOCK TABLES `Appartiene` WRITE;
/*!40000 ALTER TABLE `Appartiene` DISABLE KEYS */;
INSERT INTO `Appartiene` VALUES (4,1),(11,1),(5,2),(12,2),(13,3),(1,6),(3,6),(2,7),(7,7),(4,8),(14,8),(8,9),(10,10),(15,10),(16,10);
/*!40000 ALTER TABLE `Appartiene` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GranPremio`
--

DROP TABLE IF EXISTS `GranPremio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GranPremio` (
  `DataGP` date NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Giri` int(11) NOT NULL,
  `Nazione` varchar(45) DEFAULT NULL,
  `Circuito` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DataGP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GranPremio`
--

LOCK TABLES `GranPremio` WRITE;
/*!40000 ALTER TABLE `GranPremio` DISABLE KEYS */;
INSERT INTO `GranPremio` VALUES ('2009-03-29','ING Australian Grand Prix',58,'Australia','Melbourne Grand Prix Circuit'),('2009-04-05','Petronas Malaysian Grand Prix',56,'Malesia','Sepang International Circuit'),('2009-04-19','Chinese Grand Prix',56,'Cina','Shanghai International Circuit'),('2009-04-26','Gulf Air Bahrain Grand Prix',57,'Baharain','Bahrain International Circuit'),('2009-05-10','Gran Premio de España Telefónica',66,'Spagna','Circuit de Catalunya'),('2009-05-24','Grand Prix de Monaco',78,'Monaco','Circuit de Monaco'),('2009-09-13','Gran Premio Santander d\'Italia',53,'Italia','Autodromo Nazionale di Monza');
/*!40000 ALTER TABLE `GranPremio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pilota`
--

DROP TABLE IF EXISTS `Pilota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pilota` (
  `CodPilota` int(11) NOT NULL,
  `Nazionalita` varchar(45) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  PRIMARY KEY (`CodPilota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pilota`
--

LOCK TABLES `Pilota` WRITE;
/*!40000 ALTER TABLE `Pilota` DISABLE KEYS */;
INSERT INTO `Pilota` VALUES (1,'ING','Jenson','Button'),(2,'GER','Sebastian','Vettel'),(3,'BRA','Rubens','Barrichello'),(4,'ITA','Giancarlo','Fisichella'),(5,'ING','Lewis','Hamilton'),(7,'AUS','Mark','Webber'),(8,'ITA','Jarno','Trulli'),(9,'FIN','Kimi ','Räikkönen'),(10,'SPA',' Fernando ','Alonso'),(11,'BRA','Felipe','Masse'),(12,'FIN','Heikki ','Kovalainen'),(13,'POL','Robert ','Kubica'),(14,'GER',' Adrian ','Sutil'),(15,'FRA','Romain ','Grosjean'),(16,'BRA','Nelson ','Piquet ');
/*!40000 ALTER TABLE `Pilota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Risultato`
--

DROP TABLE IF EXISTS `Risultato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Risultato` (
  `Pilota` int(11) NOT NULL,
  `Squadra` int(11) NOT NULL,
  `GranPremio` date NOT NULL,
  `GiriEffettuati` int(11) DEFAULT NULL,
  `Punti` int(11) DEFAULT NULL,
  `Posizione` int(11) DEFAULT NULL,
  `MotivoRitiro` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Pilota`,`Squadra`,`GranPremio`),
  KEY `datagp_fk1_idx` (`GranPremio`),
  KEY `idsquadra_fk1_idx` (`Squadra`),
  CONSTRAINT `cod_pilota_fk1` FOREIGN KEY (`Pilota`) REFERENCES `Pilota` (`CodPilota`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `datagp_fk1` FOREIGN KEY (`GranPremio`) REFERENCES `GranPremio` (`DataGP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idsquadra_fk1` FOREIGN KEY (`Squadra`) REFERENCES `Squadra` (`idSQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Risultato`
--

LOCK TABLES `Risultato` WRITE;
/*!40000 ALTER TABLE `Risultato` DISABLE KEYS */;
INSERT INTO `Risultato` VALUES (1,6,'2009-03-29',58,10,1,NULL),(1,6,'2009-04-05',56,10,1,NULL),(1,6,'2009-04-19',56,8,3,NULL),(1,6,'2009-04-26',57,10,1,NULL),(1,6,'2009-05-10',66,10,1,NULL),(1,6,'2009-05-24',78,10,1,NULL),(1,6,'2009-09-13',53,9,2,NULL),(2,7,'2009-04-19',56,10,1,NULL),(2,7,'2009-04-26',57,9,2,NULL),(2,7,'2009-05-24',5,NULL,NULL,'Guasto motore'),(3,6,'2009-03-29',58,9,2,NULL),(3,6,'2009-04-05',56,6,5,NULL),(3,6,'2009-04-26',57,6,5,NULL),(3,6,'2009-05-10',66,9,2,NULL),(3,6,'2009-05-24',78,9,2,NULL),(3,6,'2009-09-13',53,10,1,NULL),(5,2,'2009-04-26',57,7,4,NULL),(7,7,'2009-04-19',56,9,2,NULL),(7,7,'2009-05-10',66,8,3,NULL),(8,9,'2009-03-29',58,8,3,NULL),(8,9,'2009-04-05',56,7,4,NULL),(8,9,'2009-04-26',57,8,3,NULL),(8,9,'2009-05-10',32,NULL,NULL,'Penalty'),(9,1,'2009-05-10',8,NULL,NULL,'Guasto motore'),(9,1,'2009-05-24',78,8,3,NULL),(9,1,'2009-09-13',53,8,3,NULL),(10,10,'2009-03-29',58,6,5,NULL),(10,10,'2009-09-13',53,6,5,NULL),(11,1,'2009-03-29',12,NULL,NULL,'Guasto motore'),(11,1,'2009-04-19',0,NULL,NULL,'Squalifica'),(11,1,'2009-05-24',78,7,4,NULL),(13,3,'2009-04-05',11,NULL,NULL,'Guasto motore'),(13,3,'2009-05-10',66,1,11,NULL),(13,3,'2009-09-13',46,NULL,NULL,'Squalifica');
/*!40000 ALTER TABLE `Risultato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Squadra`
--

DROP TABLE IF EXISTS `Squadra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Squadra` (
  `idSQ` int(11) NOT NULL,
  `NomeSQ` varchar(45) NOT NULL,
  `Motore` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSQ`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Squadra`
--

LOCK TABLES `Squadra` WRITE;
/*!40000 ALTER TABLE `Squadra` DISABLE KEYS */;
INSERT INTO `Squadra` VALUES (1,'Scuderia Ferrari Marlboro','Ferrari 056'),(2,'Vodafone McLaren Mercedes','Mercedes FO 108W'),(3,'BMW Sauber F1 Team','BMW P86/9'),(4,'Scuderia Toro Rosso','Ferrari 056'),(5,'AT&T Williams F1 Team','Toyota RVX-09'),(6,'Brawn GP F1 Team[44]','Mercedes FO 108W'),(7,'Red Bull Racing','Renault RS27'),(8,'Force India F1 Team','Mercedes FO 108W'),(9,'Panasonic Toyota Racing','Toyota RVX-09'),(10,'Renault F1 Team','Renault RS27');
/*!40000 ALTER TABLE `Squadra` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-18 23:00:25
