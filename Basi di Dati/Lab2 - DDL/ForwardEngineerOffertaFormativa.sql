-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Corso_Di_Laurea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Corso_Di_Laurea` (
  `Codice` INT NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `Tipologia` ENUM("Triennale", "Magistrale") NULL,
  PRIMARY KEY (`Codice`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Docente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Docente` (
  `Matricola` INT NOT NULL,
  `Nome` VARCHAR(20) NULL,
  `Cognome` VARCHAR(20) NULL,
  `Citta_di_residenza` VARCHAR(45) NULL,
  `Ruolo` ENUM("Associato", "Ordinario", "Ricercatore") NULL,
  `Corso_Di_Laurea_Codice` INT NULL,
  PRIMARY KEY (`Matricola`),
  INDEX `fk_Docente_Corso_Di_Laurea_idx` (`Corso_Di_Laurea_Codice` ASC) VISIBLE,
  CONSTRAINT `fk_Docente_Corso_Di_Laurea`
    FOREIGN KEY (`Corso_Di_Laurea_Codice`)
    REFERENCES `mydb`.`Corso_Di_Laurea` (`Codice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Corso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Corso` (
  `Codice` VARCHAR(20) NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `Crediti_lezione` INT NULL,
  `Ore_di_lezione` INT NULL,
  `Ore_di_esercitazione` INT NULL,
  `Crediti_esercitazione` INT NULL,
  `Docente_Matricola` INT NOT NULL,
  PRIMARY KEY (`Codice`),
  INDEX `fk_Corso_Docente1_idx` (`Docente_Matricola` ASC) VISIBLE,
  CONSTRAINT `fk_Corso_Docente1`
    FOREIGN KEY (`Docente_Matricola`)
    REFERENCES `mydb`.`Docente` (`Matricola`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CorsoLaureaCorso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`CorsoLaureaCorso` (
  `Corso_Di_Laurea_Codice` INT NOT NULL,
  `Corso_Codice` VARCHAR(20) NOT NULL,
  INDEX `fk_CorsoLaureaCorso_Corso_Di_Laurea1_idx` (`Corso_Di_Laurea_Codice` ASC) VISIBLE,
  INDEX `fk_CorsoLaureaCorso_Corso1_idx` (`Corso_Codice` ASC) VISIBLE,
  PRIMARY KEY (`Corso_Di_Laurea_Codice`, `Corso_Codice`),
  CONSTRAINT `fk_CorsoLaureaCorso_Corso_Di_Laurea1`
    FOREIGN KEY (`Corso_Di_Laurea_Codice`)
    REFERENCES `mydb`.`Corso_Di_Laurea` (`Codice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CorsoLaureaCorso_Corso1`
    FOREIGN KEY (`Corso_Codice`)
    REFERENCES `mydb`.`Corso` (`Codice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
