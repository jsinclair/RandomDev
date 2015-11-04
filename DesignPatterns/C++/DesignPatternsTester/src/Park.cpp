#include "Park.h"

using namespace std;

Park::Park(string parkName)
{
    this->parkName = parkName;

    open = true;

    exists = true;
}

Park::~Park()
{
    //dtor
}

bool Park::isOpen() {
    return open;
}

void Park::closePark() {
    open = false;
}

void Park::openPark() {
    open = true;
}

bool Park::parkExists() {
    return exists;
}

void Park::destroyPark() {
    open = false;
    exists = false;
}

void Park::addParkEntity(ParkEntity* entity) {
    entities.push_back(entity);
}

void Park::giveCommand(string command) {
    for (const auto& entity : entities) {
        entity->inputReceived(command);
    }
}

void Park::onLoop() {
    for (const auto& entity : entities) {
        entity->onParkLoop();
    }
}
