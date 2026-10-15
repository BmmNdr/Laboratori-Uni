 //Programmazione_dinamica/05_Palindromo/palindromo.cpp
#include <iostream>
#include <string>
#include <tuple>

template <typename T>
T** init_matrix(int m, int n); 

struct Solution {
    int** c;
    std::tuple<int, int>** b;

    int max_sol;
    int i_max_sol;
    int j_max_sol;

    Solution(int n, int m) {
        c = init_matrix<int>(m, n);
        b = init_matrix<std::tuple<int, int>>(m, n);

        max_sol = 0;
        i_max_sol = 0;
        j_max_sol = 0;
    }
};

Solution lics(std::string X, std::string Y);
void printMatrix(int** m, int r, int c);
void print_licsv(int i, int j, std::tuple<int, int>** b, int** c, std::string X);

int main(int argc, char const *argv[])
{
    std::cout << "Longest Increasing Commons Subsequence Problem" << std::endl;
 
    std::string X = "abcbadeg";
    std::string Y = "dabbbaef"; 

    Solution s = lics(X, Y);
    std::cout << "Print Coeff Matrix: " << std::endl;
    printMatrix(s.c, X.size(), Y.size());

    std::cout << "LICS(X,Y)= ";
    print_licsv(s.i_max_sol, s.j_max_sol, s.b, s.c, X);

     std::cout << std::endl << "|LICS(X,Y)|= " << s.max_sol;

    return 0;
}

template <typename T>
T** init_matrix(int m, int n){
    T** c = new T*[m];
    for (int i = 0; i < m; i++)
        c[i] = new T[n];

    return c;
}

void printMatrix(int** m, int r, int c) {
    for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
            std::cout << m[i][j] << " ";
        }
        std::cout << std::endl;
    }
}

Solution lics(std::string X, std::string Y){
    int m = X.size();
    int n = Y.size();

    Solution s = Solution(m, n);

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            // Caso Base
            if (X[i] != Y[j]) {
                s.c[i][j] = 0;
                // Nella ricostruzione non devo andare indietro
                s.b[i][j] = std::make_tuple(0,0);
            }
            // Passo Ricorsivo
            else {
                int tmp = 0; // in questo modo max(empty) = 0
                int i_max_tmp = 0;
                int j_max_tmp = 0;

                // Calcolo max Z^(h,k) tale che x_h < x_i
                for (int h = 0; h < i - 1; h++) {
                    for (int k = 0; k < j - 1; k++) {
                        if (X[h] < X[i] && s.c[h][k] > tmp) {
                            tmp = s.c[h][k];
                            i_max_tmp = h;
                            j_max_tmp = k;
                        }
                    }
                }

                s.c[i][j] = tmp + 1;
                // Coordinate/puntatore per la ricostrazione (indica da dove ho preso il massimo)
                s.b[i][j] = std::make_tuple(i_max_tmp, j_max_tmp);

                // Aggiorno la soluzione (per non dovere fare un doppio for per trovarla)
                if (s.c[i][j] > s.max_sol) {
                    s.max_sol = s.c[i][j];
                    s.i_max_sol = i;
                    s.j_max_sol = j; 
                }
            }
        }
    }

    return s;
}

void print_licsv(int i, int j, std::tuple<int, int>** b, int** c, std::string X) {
    if (c[i][j] == 0) {
        std::cout << "Non Esiste LICSv di Xi Yj";
    }
    else {
        // Caso Base Raggiunto
        if (b[i][j] == std::make_tuple(0, 0)) {
            std::cout << X[i] << " ";
        } 
        // Ricorsione
        else {
            int next_i = std::get<0>(b[i][j]);
            int next_j = std::get<1>(b[i][j]);

            print_licsv(next_i, next_j, b, c, X);
            std::cout << X[i] << " ";
        }
    }
}