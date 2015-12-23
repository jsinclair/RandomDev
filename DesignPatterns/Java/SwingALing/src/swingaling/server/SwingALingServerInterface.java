/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.server;

import java.util.List;
import swingaling.entities.SwingALingEntity;

/**
 *
 * @author jamesTemp
 */
public interface SwingALingServerInterface {
    public void exit();
    
    public boolean isRunning();
    
    public List<SwingALingEntity> getEntities();
    
    public void addEntity(SwingALingEntity entity);
}
