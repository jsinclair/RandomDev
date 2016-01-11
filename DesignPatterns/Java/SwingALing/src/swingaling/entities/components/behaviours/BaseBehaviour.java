/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.entities.components.behaviours;

import swingaling.entities.SwingALingEntity;

/**
 *
 * @author jamesTemp
 */
public abstract class BaseBehaviour {
    protected final SwingALingEntity entity;
    
    public BaseBehaviour(SwingALingEntity entity) {
        this.entity = entity;
    }
    
    public abstract void executeBehaviour(long timePassed);
    
    public abstract void reset();
}
