#ifndef PARKMANAGER_H
#define PARKMANAGER_H

#include <string>
#include <thread>

#include "Park.h"
#include "../src/ParkEntities.cpp"

class ParkManager
{
    public:
        ParkManager(std::string parkName);
        virtual ~ParkManager();

        bool isOpen();
        void closePark();
        void openPark();

        bool parkExists();
        void destroyPark();

        void addParkEntity(ParkEntity* entity);

        void giveCommand(string command);
    protected:
    private:
        Park *park;
        bool parkThreadRunning = false;

        void initPark();

        std::thread parkThread;
};

#endif // PARKMANAGER_H
