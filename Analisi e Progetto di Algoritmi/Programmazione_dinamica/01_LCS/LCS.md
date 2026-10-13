# LCS - Longest Common Subsequence
Progetto con codice esempio per la risoluzione di LCS tra due String

## Problema
**Istanza**: $X_m=<x_1,...,x_m>$ e $Y_n=<y_1,...,y_n>$ su alfabeto $\Sigma$
**Soluzione**: $Z$ sottosequenza sia di $X$ che di $Y$ t.c. $|Z|=max\{|W|:W$ sottoseq. sia di $X$ che di $Y\}$
indichiamo che $Z=LCS(X_m,Y_n)$.

## Sottoproblemi $(i,j)$
**Istanza**: $X_i=<x_1,...,x_i>$ e $Y_j=<y_1,...,y_j>$ con $0\le i\le m$ e con $0\le j\le n$
**Soluzione**: $Z^{(i,j)}$ piu' lunga sottosequenza sia di $X_i$ che di $Y_j$
**Coefficiente**: $c_{i,j}=|Z^{(i,j)}|$

### Ricorsione
**Caso Base**: $i=0 \vee j=0 \vee j=i=0$ allora $Z^{(i,j)}=<>$ e $c_{i,j}=0$
**Passo Ricorsivo**: tutti i sottoproblemi $(i,j)$ con $i>0 \wedge j>0$
1. ***se*** $x_i=y_j$ allora $Z^{(i,j)}=Z^{(i-1,j-1)} \mid x_i$ e $c_{i,j}=c_{i-1,j-1}+1$
2. ***se*** $x_i \not=y_j$ allora  $Z^{(i,j)}=$ $\begin{cases} Z^{(i-1,j)}\quad se\ c_{i-1,j} \ge c_{i,j-1} \\ Z^{(i,j-1)}\quad altrimenti \end{cases}$ e $c_{i,j}=max\{c_{i-1,j},c_{i,j-1}\}$
