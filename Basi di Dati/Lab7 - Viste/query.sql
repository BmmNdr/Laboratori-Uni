USE mondiale2009;

-- 1. Stilare una classifica dei piloti (composta da codice pilota, nome, cognome, 
-- nazionalità e totale dei punti), ordinandola per punteggio decrescente;
SELECT p.CodPilota, p.Nome, p.Cognome, p.Nazionalita, SUM(r.Punti) AS PT
FROM pilota p JOIN risultato r ON p.CodPilota = r.Pilota
GROUP BY p.CodPilota, p.Nome, p.Cognome,  p.Nazionalita
ORDER BY SUM(r.Punti) DESC;

-- 2. Stilare una classifica delle squadre (composta da id squadra, nome, motore e 
-- totale dei punti), ordinandola per punteggio decrescente;
SELECT s.idSQ, s.NomeSQ, s.Motore, SUM(r.Punti) AS PT
FROM squadra s JOIN appartiene a ON a.Squadra = s.idSQ
               JOIN risultato r ON r.Pilota = a.Pilota
GROUP BY s.idSQ, s.NomeSQ, s.Motore
ORDER BY SUM(r.Punti) DESC;

-- 3. Relativamente ai piloti che hanno partecipato a meno di 4 gran premi, 
-- selezionare il nome dei piloti e il numero di gran premi disputati;
SELECT *
FROM pilota p
WHERE p.CodPilota IN (
	SELECT p1.CodPilota
    FROM pilota p1 JOIN risultato r ON r.Pilota = p1.CodPilota
    WHERE r.Posizione IS NOT NULL
    GROUP BY p1.CodPilota
    HAVING COUNT(*) <= 4
);

-- 4. Per ciascun pilota, selezionare il nome e il numero di gare in cui ha effettuato 
-- almeno il 50% dei giri totali;
SELECT p.Nome, COUNT(*)
FROM pilota p JOIN risultato r ON r.Pilota = p.CodPilota
			  JOIN granpremio gp ON r.GranPremio = gp.DataGP
WHERE r.GiriEffettuati >= (gp.Giri * 0.5)
GROUP BY p.CodPilota;

-- 5. Per ciascun gran premio, indicarne il nome, la nazione, il circuito e il numero di 
-- piloti che si sono ritirati;
SELECT gp.Nome, gp.Nazione, gp.Circuito, COUNT(*)
FROM granpremio gp JOIN risultato r ON r.GranPremio = gp.DataGP
WHERE r.Posizione IS NULL
GROUP BY gp.Nome, gp.Nazione, gp.Circuito;

-- 6. Effettuare una classifica delle cause di ritiro (escludendo il valore NULL);
SELECT r.MotivoRitiro, COUNT(*)
FROM risultato r
WHERE r.MotivoRitiro IS NOT NULL
GROUP BY r.MotivoRitiro
ORDER BY COUNT(*) DESC;

-- 7. Individuare i piloti che hanno corso per due squadre diverse. Stampare il nome e 
-- cognome dei piloti ed il nome delle due squadre;
SELECT p.Cognome, s.NomeSQ
FROM squadra s JOIN appartiene a ON a.Squadra = s.idSQ
			   JOIN pilota p ON p.CodPilota = a.Pilota
WHERE p.CodPilota IN (
	SELECT a1.Pilota
	FROM appartiene a1
	GROUP BY a1.Pilota
	HAVING COUNT(*) = 2
);

-- 8. Individuare le squadre per cui hanno corso più di due piloti. Stampare il nome 
-- della squadra ed il numero di piloti corrispondente.
SELECT s.NomeSQ, COUNT(*) AS num_piloti
FROM appartiene a1 JOIN squadra s ON s.idSQ = a1.Squadra
GROUP BY a1.Squadra
HAVING COUNT(*) >= 2;