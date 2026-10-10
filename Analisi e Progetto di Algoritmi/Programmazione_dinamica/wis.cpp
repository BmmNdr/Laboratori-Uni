//Programmazione_dinamica/wis.cpp
#include <algorithm>
#include <vector>
#include <iostream>

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

    bool operator < (const Activity &iData) const {
        return end < iData.end;
    }

    bool isCompatible(Activity a2) {
        return a2.end <= start;
    }
};

std::vector<Activity> initExample();
int* compatibilityVector(std::vector<Activity> activities);
void printVector(int* v, int n);

int main(int argc, char const *argv[])
{
    std::vector<Activity> activities = initExample();
    std::sort(activities.begin(), activities.end());

    int* p = compatibilityVector(activities);
    //printVector(p, activities.size());

    //TODO WIS

    return 0;
}



int* compatibilityVector(std::vector<Activity> activities) {
    int* p = new int[activities.size()];
    for (int i = 0; i < activities.size(); i++){
        p[i] = -1;
    }

    for (int i = activities.size() - 1; i >= 0; i--){
        for (int j = i - 1; j >= 0; j--){
            if( activities.at(i).isCompatible(activities.at(j)) ){
                p[i] = j;
                break;
            }
        }   
    }

    return p;
}

void printVector(int* v, int n){
    for (int i = 0; i < n; i++){
        std::cout << v[i] << " " << std::endl;
    }
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