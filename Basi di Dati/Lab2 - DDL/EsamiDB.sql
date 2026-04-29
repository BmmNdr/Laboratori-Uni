CREATE DATABASE IF NOT EXISTS univ;

CREATE TABLE IF NOT EXISTS univ.Studente (
	matricola VARCHAR(6) NOT NULL PRIMARY KEY,
    cognome VARCHAR(40) NOT NULL,
    nome VARCHAR(40) NOT NULL,
    citta_nascita VARCHAR(50) NOT NULL,
    citta_residenza VARCHAR(50) NOT NULL,
    corso_laurea VARCHAR(100)
) ENGINE=InnoDB;							

CREATE TABLE IF NOT EXISTS univ.Corso (
	codice VARCHAR(10) NOT NULL PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    ore_lezione INT NOT NULL,
    crediti_lezione INT NOT NULL,
    docente VARCHAR(45) NOT NULL,
    ore_esercitazione INT NOT NULL,
    crediti_esercitazione INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS univ.Esame (
	matricola VARCHAR(6) NOT NULL,
    codice_corso VARCHAR(10) NOT NULL,
    data_esame DATE NOT NULL,
    voto TINYINT NOT NULL,
    CONSTRAINT uno_studente UNIQUE (matricola, codice_corso, data_esame),
    CONSTRAINT fk_studente
		FOREIGN KEY (matricola)
        REFERENCES univ.studente (matricola)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
	CONSTRAINT fk_corso
		FOREIGN KEY (codice_corso)
        REFERENCES univ.corso (codice)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;

ALTER TABLE univ.studente
	ADD COLUMN data_laurea DATE NULL DEFAULT NULL,
    ADD COLUMN titolo_tesi VARCHAR(100) NULL DEFAULT NULL;
    
ALTER TABLE univ.esame
	ADD COLUMN con_lode BOOLEAN NULL DEFAULT false;
    
RENAME TABLE univ.studente TO univ.studemte_iscritto;

DROP TABLE univ.esame;

DROP DATABASE univ;