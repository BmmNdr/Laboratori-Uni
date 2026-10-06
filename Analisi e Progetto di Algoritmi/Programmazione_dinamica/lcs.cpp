//Programmazione_dinamica/lcs.cpp
#include <ostream>
#include <string>
#include <iostream>

#define LEN 4

int** lcs(std::string X, std::string Y);
int** init_matrix(int m, int n);
void print_lcs(int i, int j, std::string X, std::string Y, int** c);
void print_coeff_matrix(int** c, int m, int n, std::string X, std::string Y);

int main(int argc, char const *argv[])
{
    std::string X = "abcbdab";
    std::string Y = "bdcaba";
    int m = X.length();
    int n = Y.length();

    int** c = lcs(X, Y);

    std::cout << "Longest Commont Subsequence Problem" << std::endl;
    std::cout << "X=<" + X + ">" << std::endl << "Y=<" + Y + ">" << std::endl;
    std::cout << "LCS(X,Y)=";
    print_lcs(m, n, X, Y, c);
    std::cout << std::endl << "|LCS(X,Y)|=" << c[m][n] << std::endl;
    std::cout << "Coefficient Matrix:" << std::endl;
    print_coeff_matrix(c, m + 1, n + 1, X, Y);

    return 0;
}

// Iterative Bottom-Up
int** lcs(std::string X, std::string Y) {

    // Init Variables
    int m = X.length();
    int n = Y.length();
    int** c = init_matrix(m + 1,n + 1);
    
    // Base Case
    for (int  j = 0; j < n + 1; j++)
        c[0][j] = 0;
    
    for (int  i = 0; i < m + 1; i++)
        c[i][0] = 0;

    // Recursive Steps
    for (int i = 1; i < m + 1; i++) {
        for (int j = 1; j < n + 1; j++) { 
            // I need to adjust the indexes of the string because
            // the matrix as one more column and row added
            if (X.at(i - 1) == Y.at(j - 1)){
                c[i][j] = c[i - 1][j - 1] + 1;
            }
            else {
                if (c[i - 1][j] >= c[i][j - 1]){
                    c[i][j] = c[i - 1][j];
                }
                else {
                    c[i][j] = c[i][j - 1];  
                }
            }
        }
    }

    return c;
    
}

// Head Recursion
void print_lcs(int i, int j, std::string X, std::string Y, int** c){
    // Base Case
    if (i == 0 || j == 0)
        return;

    // Recursive Steps
    if (X.at(i - 1) == Y.at(j - 1)) {
        print_lcs(i - 1, j - 1, X, Y, c);
        std::cout << X.at(i - 1);
    }
    else {
        if (c[i][j] == c[i - 1][j])
            print_lcs(i - 1, j, X, Y, c);
        else
            print_lcs(i, j - 1, X, Y, c);
    }
}

int** init_matrix(int m, int n){
    int** c = new int*[m];
    for (int i = 0; i < m; i++)
        c[i] = new int[n];

    return c;
}

void print_coeff_matrix(int** c, int m, int n, std::string X, std::string Y){
    
    std::cout << "     ";
    for (int j = 0; j < n - 1; j++) std::cout << Y.at(j) << "  ";
    std::cout << std::endl;
    for (int i = 0; i < m; i++)
    {
        if(i != 0) std::cout << X.at(i - 1) << " ";
        else std::cout << "  ";
        
        for (int j = 0; j < n; j++)
        {
            std::cout << c[i][j] << "  ";
        }
        std::cout << std::endl;
    }
    
}