//
// Created by sophie on 07/10/24.
//
#include <iostream>
#include <math.h>
#include "Grid.h"

using namespace std;

void initGrid(char* g, size_t t) {
    for (int i = 0; i < t*t; i++)
        g[i] = i + '0';
    g[t*t] = '0';
    g[t*t+1] = '0';
    g[t*t+2] = '\0';
    for (int i=0; i<150; i++) {
        int val = rand() % 4;
        switch (val) {
            case 0 :
                moveFromDown(g, t);
                break;
            case 1 :
                moveFromUp(g, t);
                break;
            case 2 :
                moveFromLeft(g, t);
                break;
            case 3 :
                moveFromRight(g, t);
                break;
        }
    }
}

bool isParent(char *g, size_t t) {
    if (g[t*t+1] == '0')
        return false;
    else
        return true;

}

void makeParent(char *g, size_t t) {
    g[t*t+1] = 1+'0';
}
int emptyCase(char *g, size_t t) {
    return g[t*t]-'0';
}

bool moveFromDown(char *g, size_t t) {
 bool success = false;
 int vide = emptyCase(g,t);
 int l = vide/t;
 int c = vide%t;
 if (l!=t-1) {
     g[l*t+c] = g[(l+1)*t+c];
     g[(l+1)*t+c] = '0';
     g[t*t]='0'+(l+1)*t+c;
     success=true;
 }
  return success;
}
bool moveFromUp(char *g, size_t t) {
    bool success = false;
    int vide = emptyCase(g,t);
    int l = vide/t;
    int c = vide%t;
    if (l!=0) {
        g[l*t+c] = g[(l-1)*t+c];
        g[(l-1)*t+c] = '0';
        g[t*t]='0'+(l-1)*t+c;
        success=true;
    }
    return success;
}
bool moveFromLeft(char *g, size_t t) {
    bool success = false;
    int vide = emptyCase(g,t);
    int l = vide/t;
    int c = vide%t;
    if (c!=0) {
        g[l*t+c] = g[(l*t+c-1)];
        g[l*t+c-1] = '0';
        g[t*t]='0'+l*t+c-1;
        success=true;
    }
    return success;
}
bool moveFromRight(char *g, size_t t) {
    bool success = false;
    int vide = emptyCase(g,t);
    int l = vide/t;
    int c = vide%t;
    if (c!=t-1) {
        g[l*t+c] = g[(l*t+c+1)];
        g[l*t+c+1] = '0';
        g[t*t]='0'+l*t+c+1;
        success=true;
    }
    return success;
}
int distance(char *g, size_t t) {
  int dist = 0;
  for (int i=0; i<t*t; i++)
    if (g[i]!='0') {
        int l = (g[i] - '0') / t;
        int c = (g[i] - '0') % t;
        dist += abs(l - (i / (int) t)) + abs(c - (i % (int) t));
    }
  return dist;
}

bool isFinal(char *g, size_t t)
{
  bool test = true;
  for (int i=0; i<t*t; i++)
      if (g[i]!=i+'0')
          test = false;
  return test;
}
void print(char *g, size_t t){
    for (int i=0; i<t; i++) {
        for (int j = 0; j < t; j++)
            cout << g[(i * t + j)] - '0' << " ";
        cout << endl;
    }
}
