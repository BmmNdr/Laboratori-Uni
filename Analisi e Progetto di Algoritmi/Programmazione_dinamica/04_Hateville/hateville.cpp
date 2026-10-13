//Programmazione_dinamica/04_Hateville/hateville.cpp
#include <iostream>
#include <ostream>

#define N 6

int* hateville(int X[N], int n);
void print_hv(int i, int* c, int X[N]);
void printArray(int* v, int n);

int main(int argc, char const *argv[])
{
    std::cout << "Hateville Problem" << std::endl;
    
    int X[N] = {2, 10, 5, 2, 12, 1};
    std::cout << "Input: ";
    printArray(X, N);
    std::cout << std::endl;

    int* c = hateville(X, N);
    std::cout << "Coeff. array: ";
    printArray(c, N + 1);
    std::cout << std::endl;

    std::cout << "Solution S ={ ";
    print_hv(N, c, X);
    std::cout << "}" << std::endl;

    std::cout << "Max Value: " << c[N - 1] << std::endl;

    return 0;
}

int* hateville(int X[N], int n){
    int* c = new int[n + 1];
    c[0] = 0;
    c[1] = X[0];

    for (int i = 2; i <= n; i++) 
        c[i] = std::max(c[i - 1], c[i-2] + X[i - 1]);
    
    return c;
}

void print_hv(int i, int* c, int X[N]) {
    if (i == 0) return;
    if (i == 1)
        std::cout << i << " ";
    else {
        if (c[i - 1] > c[i - 2] + X[i - 1])
            print_hv(i - 1, c, X);
        else {
            print_hv(i - 2, c, X);
            std::cout << i << " ";
        }
    }
}

void printArray(int* v, int n){
    for (int i = 0; i < n; i++){
        std::cout << v[i] << " ";
    }
}