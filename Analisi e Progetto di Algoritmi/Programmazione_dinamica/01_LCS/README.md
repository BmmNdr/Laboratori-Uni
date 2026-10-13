# LCS - Longest Common Subsequence

Progetto con codice esempio per la risoluzione di LCS tra due String

## Problema

**Istanza**: $`X_m=\langle x_1,\dots,x_m\rangle`$ e $`Y_n=\langle y_1,\dots,y_n\rangle`$ su alfabeto $`\Sigma`$

**Soluzione**: $`Z`$ sottosequenza sia di $`X`$ che di $`Y`$ t.c. $`|Z|=\max\{|W| : W \text{ sottosequenza sia di } X \text{ che di } Y\}`$

indichiamo che $`Z=LCS(X_m,Y_n)`$.

## Sottoproblemi $`(i,j)`$

**Istanza**: $`X_i=\langle x_1,\dots,x_i\rangle`$ e $`Y_j=\langle y_1,\dots,y_j\rangle`$ con $`0\le i\le m`$ e con $`0\le j\le n`$

**Soluzione**: $`Z^{(i,j)}`$ più lunga sottosequenza sia di $`X_i`$ che di $`Y_j`$

**Coefficiente**: $`c_{i,j}=|Z^{(i,j)}|`$

### Ricorsione

**Caso Base**: $`i=0 \vee j=0`$ allora $`Z^{(i,j)}=\langle\rangle`$ e $`c_{i,j}=0`$

**Passo Ricorsivo**: tutti i sottoproblemi $`(i,j)`$ con $`i>0 \wedge j>0`$

1. ***se*** $`x_i=y_j`$ allora $`Z^{(i,j)}=Z^{(i-1,j-1)} \mid x_i`$ e $`c_{i,j}=c_{i-1,j-1}+1`$
2. ***se*** $`x_i \neq y_j`$ allora $`Z^{(i,j)}=\begin{cases} Z^{(i-1,j)} & \text{se } c_{i-1,j} \ge c_{i,j-1} \\ Z^{(i,j-1)} & \text{altrimenti} \end{cases}`$ e $`c_{i,j}=\max\{c_{i-1,j},c_{i,j-1}\}`$
