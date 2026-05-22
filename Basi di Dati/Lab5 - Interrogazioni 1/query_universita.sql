USE universita;

-- 1. Selezionare tutte le città (tutti i campi) [1000+]
SELECT *
FROM citta;

-- 2. Selezionare tutte le province [1000+]
SELECT provincia
FROM citta;

-- 3. Selezionare tutte le province senza ripetizioni [107]
SELECT DISTINCT provincia
FROM citta;

-- 4. Selezionare i nomi delle città che iniziano con la lettera “M” usando funzione LEFT [827]
SELECT nome
FROM citta
WHERE LEFT(nome,1) = 'M';

-- 5. Selezionare i nomi delle città che iniziano con la lettera “M” usando funzione LIKE [827]
SELECT nome
FROM citta
WHERE nome LIKE "M%";

-- 6. Selezionare tutti gli studenti che contengono una “r” nel nome [279]
SELECT nome
FROM studente
WHERE LCASE(nome) LIKE "%r%";

-- 7. Selezionare tutti gli studenti i cui cognomi finiscono per “i”. [278]
SELECT cognome
FROM studente
WHERE LCASE(cognome) LIKE "%i";

-- 8. Selezionare gli studenti che hanno una matricola compresa tra 500 e 520 usando usando <,>,= [21]
SELECT *
FROM studente
WHERE matricola >= 500 AND matricola <= 520;

-- 9.Selezionare gli studenti che hanno una matricola compresa tra 500 e 520 usando usando BETWEEN [21]
SELECT *
FROM studente
WHERE matricola BETWEEN 500 AND 520;

-- 10. Selezionare i corsi che hanno un numero di ore di lezione maggiore a 35 [18]
SELECT *
FROM corso
WHERE ore_lezione > 35;

-- 11. Selezionare il nome del corso insieme al monte ore (ore lezione + ore esercitazione) [30]
SELECT nome, ore_lezione + ore_esercitazione AS monte_ore
FROM corso;

-- 12. Selezionare i corsi in cui il monte ore (lezione + esercitazione) è inferiore a 50 [3]
SELECT nome, (ore_lezione + ore_esercitazione) AS monte_ore
FROM corso
WHERE ore_lezione + ore_esercitazione < 50;

-- 13. Selezionare nome corso e suo monte ore se quest'ultimo è inferiore a 80 [11]
SELECT nome, (ore_lezione + ore_esercitazione) AS monte_ore
FROM corso
WHERE ore_lezione + ore_esercitazione < 80;

-- 14. Selezionare il nome del corso seguito dal nome e cognome del docente che lo insegna con un join implicito [30]
SELECT c.nome, d.nome, d.cognome
FROM docente AS d, corso AS c
WHERE d.matricola = c.docente;

-- 15. Selezionare il nome del corso seguito dal nome e cognome del docente che lo insegna con un join esplicito (JOIN ON) [30]
SELECT c.nome, d.nome, d.cognome
FROM docente AS d
	JOIN corso AS c ON d.matricola = c.docente;

-- 16. Selezionare il nome del corso di laurea seguito da nome e cognome del docente che lo presiede [3]
SELECT cdl.nome, d.nome, d.cognome
FROM corso_di_laurea AS cdl
	JOIN docente AS d ON cdl.presidente = d.matricola;

-- 17. Selezionare i dati dei corsi afferenti a corsi di laurea specialistica [10] 
SELECT c.*
FROM corso AS c
	JOIN afferisce AS a ON a.codice_corso = c.codice
    JOIN corso_di_laurea AS cdl ON a.codice_corso_laurea = cdl.codice
WHERE cdl.tipologia = "specialistica";

-- 18. Selezionare i nomi dei corsi e le info docente i cui docenti abitano a “Milano” in provincia di “MI” [2]
SELECT crs.nome, d.*
FROM corso AS crs
	JOIN docente AS d ON d.matricola = crs.docente
	JOIN citta AS ct ON d.citta_residenza = ct.codice
WHERE ct.nome = "Milano" AND ct.provincia = "MI";

-- 19. Selezionare gli studenti che hanno passato l’esame di “BASI DI DATI” con un voto superiore a 25, indicando il voto conseguito e la data [109]
SELECT s.*, e.voto, e.data
FROM studente as s
	JOIN esame AS e ON e.matricola_studente = s.matricola
    JOIN corso AS c ON c.codice = e.codice_corso
WHERE e.voto > 25 AND c.nome = "BASI DI DATI";

-- 20. Selezionare gli studenti che hanno passato l’esame di “BASI DI DATI” con un voto superiore a 25 nei 2 anni antecedenti il 04/05/2020, indicando il numero di giorni passati. [39]
SELECT s.*, e.voto, e.data, DATEDIFF(current_date(), e.data)
FROM studente as s
	JOIN esame AS e ON e.matricola_studente = s.matricola
    JOIN corso AS c ON c.codice = e.codice_corso
WHERE e.voto > 25 AND c.nome = "BASI DI DATI"
	AND e.data BETWEEN SUBDATE('2020-05-04', INTERVAL 2 YEAR) AND DATE('2020-05-04');

-- 21. Selezionare gli esami sostenuti dalla studentessa Viola Lomartire. Per ogni esame, se è stato sostenuto prima del 04/05/2018, stampare “vecchia”, altrimenti “nuova” in un campo chiamato "riforma" [14]
SELECT e.*,
	IF(
		e.data < DATE('2018-05-04'),
        "vecchia",
        "nuova"
    ) AS riforma
FROM studente AS s
	JOIN esame AS e ON e.matricola_studente = s.matricola
WHERE s.nome = "Viola" AND s.cognome = "Lomartire";