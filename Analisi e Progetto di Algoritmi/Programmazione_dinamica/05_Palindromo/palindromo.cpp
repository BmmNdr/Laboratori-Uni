//Programmazione_dinamica/05_Palindromo/palindromo.cpp
#include <iostream>
#include <string>

int** palindromo(std::string X);
void printPalindromo(int i, int j, int** c, std::string X);
void printMatrix(int** m, int r, int c);

int main(int argc, char const *argv[])
{
    std::string X = "abcaab";

    int** c = palindromo(X);


    std::cout << "Coeff Matrix: " << std::endl;
    printMatrix(c, X.size(), X.size());
    std::cout << "Number of Changes: " << c[0][X.size() - 1] << std::endl;

    printPalindromo(0, X.size() - 1, c, X);
    return 0;
}

int** palindromo(std::string X) {
    int** c = new int*[X.size()];
    for (int i = 0; i < X.size(); i++)
        c[i] = new int[X.size()];

    for (int i = X.size() - 1; i >= 0; i--) {
        for (int j = 0; j < X.size(); j++) {

            if (i >= j)
                c[i][j] = 0;

            else if (X.at(i) == X.at(j)) 
                c[i][j] = c[i + 1][j - 1];

            else
                c[i][j] = 1 + std::min(c[i + 1][j], c[i][j - 1]);
        }
    }

    return c;
}

void printPalindromo(int i, int j, int** c, std::string X) {

    if (j < 0)
        return;
    if (i + 1 >= X.size()) {
        std::cout << X.at(j);
        printPalindromo(i, j - 1, c, X);
    }
    else if (X.at(i) == X.at(j)) {
        std::cout << X.at(i);
        printPalindromo(i + 1, j - 1, c, X);
    }
    else if (c[i + 1][j] <= c[i][j - 1]){
        std::cout << X.at(i);
        printPalindromo(i + 1, j, c, X);
    }
    else {
        std::cout << X.at(j);
        printPalindromo(i, j - 1, c, X);
    }
}

void printMatrix(int** m, int r, int c) {
    for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
            std::cout << m[i][j] << " ";
        }
        std::cout << std::endl;
    }
}