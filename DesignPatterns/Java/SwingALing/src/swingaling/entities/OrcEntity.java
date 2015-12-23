/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.entities;

import java.awt.Color;
import swingaling.entities.components.behaviours.GenericIdleBehaviour;
import swingaling.server.SwingALingServerInterface;

/**
 *
 * @author jamesTemp
 */
public class OrcEntity extends SwingALingEntity {
    public OrcEntity(SwingALingServerInterface server, int x, int y) {
        super(server, x, y, 10, 10, Color.green, 100, 20);
        
        idleBehaviour = new GenericIdleBehaviour(this);
    }
}
