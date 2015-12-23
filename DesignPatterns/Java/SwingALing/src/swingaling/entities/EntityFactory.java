/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.entities;

import swingaling.gui.SwingALingGUI;
import swingaling.server.SwingALingServerInterface;

/**
 *
 * @author jamesTemp
 */
public class EntityFactory {
    public SwingALingEntity getEntity(EntityTypes type, SwingALingServerInterface server) {
        switch (type) {
            case ORC:
                return new OrcEntity(server, (int)(Math.random() * (double)SwingALingGUI.AREA_WIDTH), (int)(Math.random() * (double)SwingALingGUI.AREA_HEIGHT));
            case OGRE:
                return new OgreEntity(server, (int)(Math.random() * (double)SwingALingGUI.AREA_WIDTH), (int)(Math.random() * (double)SwingALingGUI.AREA_HEIGHT));
            default:
                return null;
        }
    }
}
