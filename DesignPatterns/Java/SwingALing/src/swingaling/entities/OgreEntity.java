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
public class OgreEntity extends SwingALingEntity {
    public OgreEntity(SwingALingServerInterface server, int x, int y, EntityFactions faction) {
        super(server, x, y, 20, 20, new Color(245, 245, 220), 80, 15, faction);
        
        idleBehaviour = new GenericIdleBehaviour(this);
    }
    
    public OgreEntity(SwingALingServerInterface server, int x, int y) {
        this(server, x, y, EntityFactions.OGRE);
    }
}
