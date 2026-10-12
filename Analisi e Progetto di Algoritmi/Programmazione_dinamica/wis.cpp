//Programmazione_dinamica/wis.cpp
#include <algorithm>
#include <vector>
#include <iostream>

// Struct for saving activity infos
struct Activity
{
    int start;
    int end;
    int weight;

    Activity(int s, int e, int w){
        start = s;
        end = e;
        weight = w;
    }

    // Define < operatore to use sort function
    bool operator < (const Activity &iData) const {
        return end < iData.end;
    }

    // Check if Activity is compatible with another activity a2.
    // Activity is compatible with a2 if it starts after a2 has ended.
    // It could be in the same instant.
    bool isCompatible(Activity a2) {
        return a2.end <= start;
    }
};

int* compatibilityVector(std::vector<Activity> activities);
int* wis(std::vector<Activity> activities, int* p);
void print_wis(int i, int* c, int* p, std::vector<Activity> activities);
void printArray(int* v, int n);
std::vector<Activity> initExample();


int main(int argc, char const *argv[])
{
    std::cout << "Weighted Interval Scheduling Problem: " << std::endl;

    std::vector<Activity> activities = initExample();
    std::sort(activities.begin(), activities.end());

    int* p = compatibilityVector(activities);
    std::cout << "Compatibility Vector: ";
    printArray(p, activities.size() + 1);

    int* c = wis(activities, p);
    std::cout << "c Vector: ";
    printArray(c, activities.size() + 1);

    std::cout << "Solution: ";
    print_wis(activities.size(), c, p, activities);

    std::cout << std::endl << "Max Weight: " << c[activities.size()] << std::endl;

    return 0;
}

// Creates the compatibility vector (p[i] is the nearest compatible activity index)
int* compatibilityVector(std::vector<Activity> activities) {
    int* p = new int[activities.size() + 1];
    for (int i = 0; i < activities.size() + 1; i++){
        p[i] = 0;
    }

    for (int i = activities.size() - 1; i >= 0; i--){
        for (int j = i - 1; j >= 0; j--){
            if( activities.at(i).isCompatible(activities.at(j)) ){
                // I need to adjust index because p has the empty problem input at p[0]
                p[i + 1] = j + 1;
                break;
            }
        }   
    }

    return p;
}

// Main function
int* wis(std::vector<Activity> activities, int* p) {
    int* c = new int[activities.size() + 1];

    c[0] = 0;
    for (int i = 1; i <= activities.size(); i++) {
        c[i] = std::max(c[i - 1], c[p[i]] + activities.at(i - 1).weight );
    }

    return c;
}

// Prints the sequence of activities
void print_wis(int i, int* c, int* p, std::vector<Activity> activities) {
    if (i != 0) {
        if (c[p[i]] + activities.at(i - 1).weight >= c[i - 1]) {
            print_wis(p[i], c, p, activities);
            std::cout << i << " ";
        } else {
            print_wis(i - 1, c, p, activities);
        }
    }
}

void printArray(int* v, int n){
    for (int i = 0; i < n; i++){
        std::cout << v[i] << " ";
    }

    std::cout << std::endl;
}

std::vector<Activity> initExample(){
    std::vector<Activity> activities = std::vector<Activity>();

    Activity a4 = Activity(5, 8, 1);
    activities.push_back(a4);
    Activity a3 = Activity(4, 6, 8);
    activities.push_back(a3);
    Activity a1 = Activity(2, 3, 10);
    activities.push_back(a1);
    Activity a2 = Activity(1, 5, 2);
    activities.push_back(a2);
    Activity a5 = Activity(4, 9, 1);
    activities.push_back(a5);
    Activity a6 = Activity(8, 10, 3);
    activities.push_back(a6);

    return activities;
}