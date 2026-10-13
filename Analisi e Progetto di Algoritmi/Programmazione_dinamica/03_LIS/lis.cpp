//Programmazione_dinamica/lis.cpp
#include <iostream>

#define N 8

struct Solution {
    int max = 0;
    int ind_m = 0;
    int c[N];
    int b[N];
};

Solution lis(int* X, int n);
void print_lisv(int i, int b[N], int X[N]);
void printArray(int* v, int n);

int main(int argc, char const *argv[])
{
    std::cout << "Longest Increasing Sequence" << std::endl;
    int X[N] = {6, 4, 7, 3, 5, 9, 11, 8};
    Solution s = lis(X, N);

    std::cout << "Index Max: " << s.ind_m << " | Value Max: " << s.max << std::endl;
    std::cout << "Coefficient Array: ";
    printArray(s.c, N);
    std::cout << "Ausiliary Problem Pointers: ";
    printArray(s.b, N);

    std::cout << "LIS(X)={ "; 
    print_lisv(s.ind_m, s.b, X);
    std::cout << "}" << std::endl;

    return 0;
}

Solution lis(int* X, int n) {
    Solution s = Solution();

    s.c[0] = 1;
    s.max = s.c[0];
    s.ind_m = 0;

    for (int i = 0; i < n; i++) {
        s.b[i] = -1;
    }

    for (int i = 1; i < n; i++) {
        int tmp = 0;
        int k = 0;

        // Searches for LIS compatible with X[i]
        for (int j = 0; j <= i - 1; j++) {
            if (X[j] < X[i] && s.c[j] > tmp) {
                tmp = s.c[j];
                k = j;
            }
        }

        s.c[i] = tmp + 1;
        s.b[i] = k;

        if (s.c[i] > s.max) {
            s.max = s.c[i];
            s.ind_m = i;
        }
    }

    return s;
}

void print_lisv(int i, int b[N], int X[N]) {
    if (b[i] == -1) {
        std::cout << X[i] << " ";
    } else {
        print_lisv(b[i], b, X);
        std::cout << X[i] << " ";
    }
}

void printArray(int* v, int n){
    for (int i = 0; i < n; i++){
        std::cout << v[i] << " ";
    }

    std::cout << std::endl;
}