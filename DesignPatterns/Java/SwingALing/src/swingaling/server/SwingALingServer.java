/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.server;

import swingaling.entities.SwingALingEntity;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author jamesTemp
 */
public class SwingALingServer implements Runnable, SwingALingServerInterface {
    private boolean running = false;
    
    private final Semaphore startupSemaphore;
    
    private final List<SwingALingEntity> entities = new CopyOnWriteArrayList<>();
    
    private long lastLoopTime = 0;
    
    public SwingALingServer(Semaphore startupSemaphore) {
        this.startupSemaphore = startupSemaphore;
        
        this.startupSemaphore.drainPermits();
    }
    
    @Override
    public void run() {
        startupSemaphore.release();
        running = true;
        
        preLoop();
        
        while(running) {
            onLoop();
        }
        
        postLoop();
    }
    
    public void preLoop() {
        System.out.println("preloop");
        
        lastLoopTime = System.currentTimeMillis();
    }
    
    public void onLoop() {
        //System.out.println("loop");
        long thisLoopTime = System.currentTimeMillis();
        if (lastLoopTime != thisLoopTime) {
            entities.stream().forEach((entity) -> {
                entity.onLoop(thisLoopTime - lastLoopTime);
            });
            
            lastLoopTime = thisLoopTime;
        }
    }
    
    public void postLoop() {
        System.out.println("postloop");
    }
    
    @Override
    public void exit() {
        running = false;
    }
    
    @Override
    public boolean isRunning() {
        return running;
    }
    
    @Override
    public List<SwingALingEntity> getEntities() {
        return entities;
    }
    
    @Override
    public void addEntity(SwingALingEntity entity) {
        entities.add(entity);
    }
}
