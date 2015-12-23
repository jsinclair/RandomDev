/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.entities.components;

/**
 *
 * @author jamesTemp
 */
public interface TimeListenerController {
    public void registerListener(TimeListener listener);
    
    public void deRegisterListener(TimeListener listener);
}
