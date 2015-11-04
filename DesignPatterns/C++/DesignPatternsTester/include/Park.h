#ifndef PARK_H
#define PARK_H

#include <string>
#include <vector>

#include "../src/ParkEntities.cpp"

class Park
{
    public:
        Park(std::string parkName);
        virtual ~Park();

        bool isOpen();
        void closePark();
        void openPark();

        bool parkExists();
        void destroyPark();

        void addParkEntity(ParkEntity* entity);

        void giveCommand(string command);

        void onLoop();
    protected:
    private:
        bool open, exists;

        std::string parkName;

        std::vector<ParkEntity*> entities;
};

#endif // PARK_H
