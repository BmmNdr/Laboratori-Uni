USE universita;

-- 1. Si selezioni lo stipendio mensile, il nome e il cognome dei docenti ordinari e i corsi da essi insegnati
SELECT ROUND(s.Valore / 12) stipendio_mensile, pd.Nome, pd.Cognome, c.Nome
FROM personale_docente pd JOIN stipendio s ON pd.Classe_stipendio = s.Classe
						  JOIN corso c ON c.Docente = pd.Matricola_d
WHERE pd.Ruolo = "Ordinario";

-- 2. Si selezionino tutti corsi (nome) appartenenti a corsi di laurea triennali, con il totale delle ore
SELECT c.Nome, (c.Ore_lezione + c.Ore_esercitazione) totale_ore
FROM corso c JOIN corso_di_laurea_corsi clc ON c.Codice_c = clc.Codice_c
			 JOIN corso_di_laurea cdl ON clc.idCorso_di_Laurea = cdl.idCorso_di_Laurea
WHERE cdl.Tipologia = "Triennale";

-- 3. Selezionare gli esami svolti dallo studente Pizzo Andrea con le relative votazioni e date
SELECT c.Nome, e.Voto, e.Data
FROM studente s JOIN esame e ON e.Matricola_studente = s.Matricola_st
				JOIN corso c ON c.Codice_c = e.Codice_corso
WHERE s.Nome = "Andrea" AND s.Cognome = "Pizzo";

-- 4. Selezionare gli esami svolti nei corsi di laurea triennale, e le relative votazioni
SELECT e.Codice_corso, cdl.Nome, e.Matricola_studente, e.Voto
FROM esame e JOIN corso_di_laurea_corsi clc ON e.Codice_corso = clc.Codice_c
			 JOIN corso_di_laurea cdl ON cdl.idCorso_di_Laurea = clc.idCorso_di_Laurea
WHERE cdl.Tipologia = "Triennale";

-- 5. Selezionare gli esami, e relativi crediti totali, svolti nei corsi di laurea triennale senza ripetizioni di record e senza votazioni
SELECT c.Codice_c, c.Nome, (c.Crediti_esercitazione + c.Crediti_lezione) crediti_totali
FROM esame e JOIN corso c ON c.Codice_c = e.Codice_corso
			 JOIN corso_di_laurea_corsi clc ON c.Codice_c = clc.Codice_c
			 JOIN corso_di_laurea cdl ON cdl.idCorso_di_Laurea = clc.idCorso_di_Laurea
WHERE cdl.Tipologia = "Triennale"
GROUP BY c.Codice_c, c.Nome;

-- 6. Si selezionino il nome, il cognome, la matricola e la città di residenza degli studenti che hanno il corso "Analisi Matematica" nel piano di studi
SELECT *
FROM studente s JOIN citta ON citta.idCitta = s.Citta_residenza
WHERE s.Matricola_st IN (
	SELECT s1.Matricola_st
	FROM studente s1 JOIN piano_di_studio pds ON s.Matricola_st = pds.Matricola_st
	JOIN corso c ON c.Codice_c = pds.Codice_corso
	WHERE c.Nome = "Analisi Matematica"
);

-- 7. Si selezionino il nome, il cognome, la matricola e la città di residenza dei docenti ordinari e associati che insegnano alla laurea triennale e che guadagnano almeno 40000
SELECT pd.Nome, pd.Cognome, citta.Nome
FROM personale_docente pd JOIN stipendio s ON s.Classe = pd.Classe_stipendio
						  JOIN citta ON citta.idCitta = pd.Citta_di_residenza
WHERE (pd.Ruolo = "Ordinario" OR pd.Ruolo = "Associato") AND s.Valore >= 40000
	AND pd.Matricola_d IN (
		SELECT pd1.Matricola_d
        FROM personale_docente pd1 JOIN corso c ON c.Docente = pd1.Matricola_d
								   JOIN corso_di_laurea_corsi cls ON c.Codice_c = cls.Codice_c
                                   JOIN corso_di_laurea cdl ON cdl.idCorso_di_Laurea = cls.idCorso_di_Laurea
		WHERE cdl.Tipologia = "Triennale"
);

-- 8. Si selezionino gli studenti triennale (nome, cognome, matricola e città di residenza) che hanno sostenuto l'esame di "Analisi Matematica" e appartengono alla regione Piemonte
SELECT s.Nome, s.Cognome, s.Matricola_st, citta.Nome
FROM studente s JOIN citta ON citta.idCitta = s.Citta_residenza
WHERE s.Matricola_st IN (
	SELECT s1.Matricola_st
	FROM studente s1 JOIN piano_di_studio pds ON s.Matricola_st = pds.Matricola_st
                     JOIN esame e ON e.Matricola_studente = s1.Matricola_st
                     JOIN corso c ON c.Codice_c = e.Codice_corso
                     JOIN corso_di_laurea cdl ON cdl.idCorso_di_Laurea = s1.Corso_di_Laurea
	WHERE c.Nome = "Analisi Matematica" AND cdl.Tipologia = "Triennale"
) AND citta.Regione = "Piemonte";

-- 9. Stilare una classifica degli studenti (con cognome, nome, matricola e voto) che hanno sostenuto l'esame con codice E3101Q117 con un voto maggiore di 23, ordinandola per voto decrescente
SELECT s.Nome, s.Cognome, s.Matricola_st, e.Voto
FROM studente s JOIN esame e ON e.Matricola_studente = s.Matricola_st
WHERE e.Codice_corso = "E3101Q117" AND e.Voto > 23
ORDER BY e.Voto DESC;

-- 10. Contare il numero di studenti che hanno superato l'esame con codice E3101Q117 con un voto maggiore di 23
SELECT COUNT(*)
FROM studente s JOIN esame e ON e.Matricola_studente = s.Matricola_st
WHERE e.Codice_corso = "E3101Q117" AND e.Voto > 23;

-- 11. Selezionare gli studenti (con nome, cognome e matricola) con la media dei voti degli esami, e ordinarli secondo la media in ordine descrescente
SELECT s.Nome, s.Cognome, s.Matricola_st, AVG(e.Voto) as media
FROM studente as s JOIN esame AS e ON s.Matricola_st = e.Matricola_studente
GROUP BY s.Matricola_st, S.Nome, S.Cognome
ORDER BY media DESC;

-- 12. Selezionare gli studenti (con nome, cognome e matricola) che iniziano con la lettera B e il numero di esami svolti;
SELECT s.Nome, s.Cognome, s.Matricola_st, COUNT(*) AS n_esame
FROM studente AS s JOIN esame AS e ON s.Matricola_st = e.Matricola_studente
GROUP BY s.Matricola_st, s.Nome, s.Cognome
HAVING s.Nome LIKE "B%";

-- 13. Selezionare gli studenti (con nome, cognome e matricola) che hanno svolto l'esame con codice E3101Q020 e/o con codice E3101Q117
SELECT s.Nome, s.Cognome, s.Matricola_st, e.Codice_corso
FROM studente AS s JOIN esame AS e ON s.Matricola_st = e.Matricola_studente
WHERE e.Codice_corso = "E3101Q020" 
UNION
SELECT s.Nome, s.Cognome, s.Matricola_st, e.Codice_corso
FROM studente AS s JOIN esame AS e ON s.Matricola_st = e.Matricola_studente
WHERE e.Codice_corso = "E3101Q117";

-- 14. Selezionare gli studenti (con nome, cognome e matricola) che hanno svolto l'esame con codice E3101Q020 e con codice E3101Q117
SELECT s.Nome, s.Cognome, s.Matricola_st, e.Codice_corso
FROM studente AS s JOIN esame AS e ON s.Matricola_st = e.Matricola_studente
WHERE e.Codice_corso = "E3101Q020" 
AND s.Matricola_st IN (
	SELECT s1.Matricola_st
	FROM studente AS s1 JOIN esame AS e1 ON s1.Matricola_st = e1.Matricola_studente
	WHERE e1.Codice_corso = "E3101Q117"
);

-- 15. Visualizzare i corsi (con codice e nome) di cui di cui lo studente con matricola 1492601 non ha ancora sostenuto l'esame
SELECT *
FROM corso c JOIN piano_di_studio pds ON c.Codice_c = pds.Codice_corso
			 JOIN studente s ON s.Matricola_st = pds.Matricola_st
WHERE c.Codice_c NOT IN (
	SELECT c1.Codice_c
    FROM studente s1 JOIN esame e ON e.Matricola_studente = s1.Matricola_st
					 JOIN corso c1 ON e.Codice_corso = c1.Codice_c
	WHERE s1.Matricola_st = "1492601"
) AND s.Matricola_st = "1492601";

-- 16. Visualizzare i corsi (con codice e nome) il cui esame deve ancora essere sostenuto da qualche studente (rispetto al relativo piano di studi) e il numero di studenti che li devono sostenere. Si 
-- considerino solo i corsi della laurea triennale e si ordinino i risultati in ordine decrescente per numero di studenti


-- 17. Visualizzare i corsi (con codice e nome) con la media dei voti conseguiti dagli studenti nei relativi esami, e il numero di studenti che li hanno sostenuti. Selezionare solo i corsi della laurea 
-- magistrale il cui esame è stato superato da almeno 60 studenti. Si ordinino i risultati in ordine decrescente rispetto alla media dei voti
SELECT c.Codice_c, c.Nome, AVG(e.Voto) media, COUNT(*) n_studenti
FROM corso c JOIN esame e ON e.Codice_corso = c.Codice_c
			 JOIN studente s ON e.Matricola_studente = s.Matricola_st
             JOIN corso_di_laurea_corsi cl ON cl.Codice_c = e.Codice_corso
WHERE cl.idCorso_di_Laurea = (
	SELECT cdl.idCorso_di_Laurea
    FROM corso_di_laurea cdl
    WHERE cdl.Tipologia = "Magistrale"
)
GROUP BY c.Codice_c, c.Nome
HAVING n_studenti >= 60
ORDER BY media DESC;

-- 18. Selezionare il miglior studente in termini di media dei voti (con cognome, nome, matricola, media e numero esami sostenuti) della laurea triennale
SELECT Cognome, Nome, Matricola_st, media, esami_sostenuti
FROM (
	SELECT s.Cognome, s.Nome, s.Matricola_st, AVG(e.Voto) media, COUNT(*) esami_sostenuti
    FROM studente s JOIN esame e ON s.Matricola_st = e.Matricola_studente
					JOIN corso_di_laurea cdl ON s.Corso_di_Laurea = cdl.idCorso_di_Laurea
	WHERE cdl.Tipologia = "Triennale"
    GROUP BY s.Cognome, s.Nome, s.Matricola_st
) AS t1
WHERE t1.media = (
	SELECT MAX(t2.media1)
    FROM (
		SELECT s2.Matricola_st, AVG(e2.Voto) media1
        FROM studente s2 JOIN esame e2 ON s2.Matricola_st = e2.Matricola_studente
						 JOIN corso_di_laurea cdl2 ON cdl2.idCorso_di_Laurea = s2.Corso_di_Laurea
		WHERE cdl2.Tipologia = "Triennale"
        GROUP BY s2.Matricola_st
    ) AS t2
)

-- 19. Visualizzare le città di residenza (con nome e regione) e il numero di studenti che provengono da tali città. Ordinare i risultati in ordine decrescente rispetto al numero di studenti


-- 20. Visualizzare gli studenti della triennale e il numero di crediti che hanno finora ottenuto


-- 21. Riscrivere le query 9, 10, 13, 14 e 15 non utilizzando i codici degli esami e le matricola nella condizione ma una stringa come ad esempio il nome