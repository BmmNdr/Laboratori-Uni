CREATE DATABASE IF NOT EXISTS OffertaFormativa;
	
CREATE TABLE IF NOT EXISTS OffertaFormativa.CorsoDiLaurea  (
	Codice INT NOT NULL PRIMARY KEY,
    Nome VARCHAR(40) NOT NULL,
    Tipologia ENUM("Magistrale", "Triennale") NOT NULL
) ENGINE=InnoDB;
    
CREATE TABLE IF NOT EXISTS OffertaFormativa.Docente (
	Matricola INT NOT NULL PRIMARY KEY,
    Nome VARCHAR(40) NOT NULL,
    Cognome VARCHAR(40) NOT NULL,
    Citta_di_residenza VARCHAR(40) NOT NULL,
    Corso_presieduto INT NOT NULL,
    Ruolo ENUM("Associato", "Ordinario", "Ricercatore") NOT NULL,
    CONSTRAINT fk_corso
		FOREIGN KEY (Corso_presieduto)
        REFERENCES OffertaFormativa.CorsoDiLaurea (Codice)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS OffertaFormativa.Corso (
	Codice INT NOT NULL PRIMARY KEY,
    Nome VARCHAR(40) NOT NULL,
    Crediti_lezione INT NOT NULL,
    Ore_lezione INT NOT NULL,
    Crediti_esercitazione INT NOT NULL,
    Ore_esercitazione INT NOT NULL,
    Matricola_docente INT NOT NULL,
    CONSTRAINT fk_docente
		FOREIGN KEY (Matricola_docente)
        REFERENCES OffertaFormativa.Docente (Matricola)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS OffertaFormativa.CorsoLaureaCorso (
	Codice_Corso INT NOT NULL,
    Codice_CorsoDiLaurea INT NOT NULL,
    PRIMARY KEY (Codice_Corso, Codice_CorsoDiLaurea),
    CONSTRAINT fk_corso_codice
		FOREIGN KEY (Codice_Corso)
        REFERENCES OffertaFormativa.Corso (Codice),
	CONSTRAINT fk_corsoDiLaurea
		FOREIGN KEY (Codice_CorsoDiLaurea)
        REFERENCES OffertaFormativa.CorsoDiLaurea (Codice)
) ENGINE=InnoDB;