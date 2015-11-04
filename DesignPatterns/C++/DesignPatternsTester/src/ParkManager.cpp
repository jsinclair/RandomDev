#include <iostream>

#include "ParkManager.h"

using namespace std;

ParkManager::ParkManager(string parkName)
{
    //ctor
    park = new Park(parkName);

    parkThread = thread(&ParkManager::initPark, this);
}

ParkManager::~ParkManager()
{
    //dtor
}

void ParkManager::initPark() {
    parkThreadRunning = true;
    while (park->parkExists()) {
        park->onLoop();
    }
    parkThreadRunning = false;
}

void ParkManager::destroyPark() {
    park->destroyPark();

    parkThread.join();
}

bool ParkManager::isOpen() {
    return park->isOpen();
}

void ParkManager::closePark() {
    park->closePark();
}

void ParkManager::openPark() {
    park->openPark();
}

bool ParkManager::parkExists() {
    return park->parkExists();
}

void ParkManager::addParkEntity(ParkEntity* entity) {
    park->addParkEntity(entity);
}

void ParkManager::giveCommand(string command) {
    if (park->isOpen()) {
        park->giveCommand(command);
    } else {
        cout << "The park is closed! No commands!" << endl;
    }
}
