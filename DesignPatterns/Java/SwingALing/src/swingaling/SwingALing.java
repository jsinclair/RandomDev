/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import swingaling.gui.SwingALingGUI;
import swingaling.server.SwingALingServer;

/**
 *
 * @author jamesTemp
 */
public class SwingALing {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting up!");
        
        // Create and initialise the server.
        Semaphore startSem = new Semaphore(0);
        SwingALingServer server = new SwingALingServer(startSem);
        
        Thread serverThread = new Thread(server);
        serverThread.start();
        
        if (startSem.tryAcquire(200, TimeUnit.MILLISECONDS)) {
            // Create the GUI.
            SwingALingGUI gui = new SwingALingGUI(server);
            gui.startDrawableThread();
            
            // Create an input reader to allow exiting for now.
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (server.isRunning()) {
                try {
                    if (br.readLine().equals("exit")) {
                        server.exit();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SwingALing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Unable to start up the server.");
        }
        
        System.out.println("Jobs done!");
    }
}
