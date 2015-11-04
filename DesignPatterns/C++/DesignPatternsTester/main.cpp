#include <iostream>

#include "ParkManager.h"

using namespace std;

int main()
{
    // initialize the park thread
    ParkManager parkManager("Radloff");

    parkManager.addParkEntity(new GoodDog("Toffee", new BarkYap(), 60));
    parkManager.addParkEntity(new GoodDog("Nicky", new BarkYip()));
    parkManager.addParkEntity(new LazyDog("Noxy", new BarkSnuffle()));

    cout << "Hello dog trainer! What would you like the pups to do?" << endl;

    string command;

    do {
        cin >> command;

        if (command.compare("exit") != 0) {
            if (command.compare("open") == 0) {
                parkManager.openPark();
            } else if (command.compare("close") == 0) {
                parkManager.closePark();
            } else {
                parkManager.giveCommand(command);
            }
        } else {
            parkManager.destroyPark();
        }
    } while (command.compare("exit") != 0);

    return 0;
}
