USE scuola;

-- Almeno 5 classi di 3 diversi anni, di cui almeno una classe del quinto anno
INSERT INTO classe (anno, sezione)
VALUES 
	(3, "A"),
    (4, "A"),
    (5, "A"),
    (5, "B"),
    (4, "C"),
    (5, "C"),
    (4, "B"),
    (4, "A"),
    (5, "A");
    
    
    
-- Almeno 10 studenti, di cui almeno 1 straniero, e 2 che siano fratello e sorella. Gli studenti 
-- devono risiedere in almeno 2 comuni diversi.
INSERT INTO comune (codice, nome, regione)
VALUES
	(1, "Seveso", "Lombardia"),
    (2, "Meda", "Lombardia");

INSERT INTO studente (codice_fiscale, nome, cognome, comune_residenza, classe_anno, classe_sezione, nazione)
VALUES
	("A", "Mario", "Rossi", 1, 3, "A", "Italia"),
    ("B", "Lucas", "Negros", 2, 3, "A", "Spagna"),
    ("C", "Anna", "Bianco", 2, 3, "A", "Italia"),
    ("D", "Paolo", "Blue", 1, 4, "B", "Italia"),
    ("E", "Paola", "Viola", 1, 4, "A", "Italia"),
    ("F", "Maria", "Rossi", 1, 4, "C", "Italia"),
    ("G", "Claudio", "Gialli", 2, 5, "B", "Italia"),
    ("H", "Claudia", "Verdi", 2, 5, "B", "Italia"),
    ("I", "Giuseppe", "Bianchi", 2, 4, "A", "Italia"),
    ("L", "Luca", "Pozzi", 1, 3, "A", "Italia");
    
INSERT INTO parentela (studente_1, studente_2, grado)
VALUES
	("A", "F", "Fratello"),
    ("F", "A", "Sorella");
    


-- Almeno 5 materie, con almeno 1 argomento ognuna
INSERT INTO materia (codice, nome)
VALUES
	(1, "APS"),
    (2, "PSI"),
    (3, "LP"),
    (4, "BD"),
    (5, "GAL");
    
INSERT INTO argomento (codice, codice_materia, nome, ore)
VALUES
	(1, 1, "GRASP", 67),
    (1, 2, "TLC", 104),
    (1, 3, "EMACS", 1),
    (2, 3, "LISP", 10),
    (1, 4, "SQL", 10),
    (1, 5, "Croissants", 25);



-- Almeno 1 propedeuticità tra argomenti
INSERT INTO propedeuticita (materia, argomento_prima, argomento_dopo)
VALUES
	(3, 1, 2);
    


-- Almeno 3 insegnanti, di cui almeno 1 insegna anche in un'altra scuola
INSERT INTO insegnante (codice_fiscale, nome, cognome, comune_residenza, comune_nascita)
VALUES
	("M", "Marco", "Antoniotts", 1, 2),
    ("N", "Bho", "Hauntion", 2, 2),
    ("O", "Paolo", "Napoletanos", 1, 1);
    
INSERT INTO scuola (codice, nome, comune)
VALUES
	(1, "Monnet", 2);
    
INSERT INTO insegnamento_altra_scuola (insegnante, codice_scuola)
VALUES
	("M", 1);
    


-- Almeno 10 valutazioni relative ad almeno 5 studenti differenti
INSERT INTO valutazione (codice_argomento, codice_materia, studente, voto)
VALUES
	(1, 1, "A", 8),
    (1, 1, "B", 8),
    (1, 1, "C", 8),
    (1, 1, "D", 8),
    (1, 1, "E", 8),
	(1, 2, "A", 8),
    (1, 2, "B", 8),
    (1, 2, "C", 8),
    (1, 2, "D", 8),
    (1, 2, "E", 8);
    
-- Creare tutte le associazioni necessarie per assegnare gli studenti alle relative classi, e per 
-- associare gli insegnanti alle materie e alle classi in cui insegnano
INSERT INTO insegnamento (insegnante, codice_materia, classe_anno, classe_sezione, ore_settimanali)
VALUES
	("M", 3, 4, "A", 12), 
    ("N", 5, 4, "B", 12),
    ("O", 4, 4, "C", 12);
    
    
    
-- Modificare il cognome di uno degli studenti (selezionandolo per codice fiscale)
UPDATE studente AS s
	SET s.cognome = "Mutellon"
WHERE s.codice_fiscale = "E";



-- Cambiare il comune di residenza di uno degli insegnanti (selezionandolo per nome e cognome)
UPDATE insegnante AS i
SET i.comune_residenza = 1
WHERE i.nome = "Bho" AND i.cognome = "Hauntion"
LIMIT 1;



-- Raddoppiare il numero di ore di lezione insegnate da un docente per una certa materia 
-- in una specifica classe
UPDATE insegnamento as i
SET i.ore_settimanali = i.ore_settimanali * 2
WHERE i.insegnante = "M" AND i.codice_materia = 3 AND i.classe_anno = 4 AND i.classe_sezione = "A";



-- Eliminare una delle propedeuticità
DELETE FROM propedeuticita as p
WHERE p.materia = 3 AND p.argomento_dopo = 2
LIMIT 1;



-- Modificare il voto di una valutazione di uno studente per uno specifico argomento
UPDATE valutazione as v
SET v.voto = 9
WHERE v.codice_argomento = 1 AND v.codice_materia = 1 AND v.studente = "A";



-- Modificare il nome di una delle scuole in cui uno specifico docente insegna (selezionando 
-- la scuola sulla base del nome e del cognome del docente che vi insegna)
SET SQL_SAFE_UPDATES = 0;
UPDATE scuola AS s
	INNER JOIN insegnamento_altra_scuola AS ias 
		ON ias.codice_scuola = s.codice
    INNER JOIN insegnante AS i 
		ON ias.insegnante = i.codice_fiscale
SET s.nome = "Bicocca"
WHERE i.nome = "Marco" AND i.cognome = "Antoniotts";



-- Simulare il Passaggio all'anno successivo
-- (eliminando gli studenti del quinto anno)
DELETE v FROM valutazione as v
	JOIN studente as s
		ON s.codice_fiscale = v.studente
WHERE studente.classe_anno = 5;

DELETE FROM parentela
WHERE studente_1 IN
	(
		SELECT codice_fiscale FROM studente
		WHERE classe_anno = 5
	)
	OR studente_2 IN
	(
		SELECT codice_fiscale FROM studente
		WHERE classe_anno = 5
	);
    
DELETE FROM studente 
	WHERE classe_anno = 5;
    
UPDATE studente
	SET classe_anno = classe_anno + 1;