#ifndef PARKENTITIES_H
#define PARKENTITIES_H

#include <time.h>
#include <stdlib.h>
#include <iostream>
#include <string>

using namespace std;

class InputListenerInterface {
public:
    virtual void inputReceived(string input) = 0;
};

class BarkInterface {
public:
    virtual void bark() = 0;
};

class BarkYap: public BarkInterface {
public:
    void bark() {
        cout << "Yap! Yap! Yap!" << endl;
    }
};

class BarkYip: public BarkInterface {
public:
    void bark() {
        cout << "Yip! Yip!" << endl;
    }
};

class BarkWoof: public BarkInterface {
public:
    void bark() {
        cout << "Woof!" << endl;
    }
};

class BarkSnuffle: public BarkInterface {
public:
    void bark() {
        cout << "Snuffle!" << endl;
    }
};

class ParkEntity: public InputListenerInterface {
public:
    virtual void onParkLoop() = 0;
};

class Dog: public ParkEntity {
public:
    Dog(string name, BarkInterface *bark, int attentionSpan = 120) {
        this->name = name;
        this->bark = bark;
        this->attentionSpan = attentionSpan;

        timeSinceInteraction = 0;
        lastDistractionCheck = 0;

        time_t seconds;

        seconds = time(NULL);

        lastLoopTime = (int)seconds;
    }

    void performBark() {
        cout << name << ": ";
        bark->bark();
    }

    void onParkLoop() {
        time_t seconds;

        seconds = time(NULL);

        timeSinceInteraction += ((int)seconds - lastLoopTime);

        lastDistractionCheck = ((int)seconds - lastLoopTime);

        lastLoopTime = (int)seconds;
        while (lastDistractionCheck > 0) {
            //cout << "Checking distraction " << timeSinceInteraction << " " << lastDistractionCheck  << endl;
            if ((rand() % attentionSpan) + timeSinceInteraction > attentionSpan && timeSinceInteraction >= 1) {
                timeSinceInteraction = 0;
                this->performBark();
            }
            lastDistractionCheck = lastDistractionCheck - 1;
        }
    }
protected:
    string name;
    BarkInterface *bark;
    int timeSinceInteraction;
private:
    int lastLoopTime, attentionSpan, lastDistractionCheck;
};

class GoodDog: public Dog {
public:
    GoodDog(string name, BarkInterface *bark, int attentionSpan = 120) : Dog(name, bark, attentionSpan) {
    }

    void inputReceived(string input) {
        cout << name << " " << input << "s." << endl;

        timeSinceInteraction = 0;
        //this->performBark();
    }
};

class LazyDog: public Dog {
public:
    LazyDog(string name, BarkInterface *bark, int attentionSpan = 120) : Dog(name, bark, attentionSpan) {
    }

    void inputReceived(string input) {
        cout << name << " snoozes." << endl;

        timeSinceInteraction = 0;
        //this->performBark();
    }
};

#endif // PARKENTITIES_H

