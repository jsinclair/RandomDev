/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.entities;

import swingaling.entities.components.TimeListenerController;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import swingaling.entities.components.TimeListener;
import swingaling.entities.components.behaviours.BaseBehaviour;
import swingaling.gui.SwingALingDrawable;
import swingaling.gui.SwingALingGUI;
import swingaling.server.SwingALingServerInterface;
import swingaling.utilities.EntityCoordinates;

/**
 *
 * @author jamesTemp
 */
public abstract class SwingALingEntity implements SwingALingDrawable, TimeListenerController {
    protected double x = 0, y = 0;
    protected final int width, height;
    private final Color color;
    
    // The number of pixels the entity can see around itself
    protected int visionRadius;
    
    // The base walking movespeed of the entity poer second.
    protected final double moveSpeed;
    
    protected final List<TimeListener> timeListenerList;
    
    // Move speed modifiers
    public static final int SPEED_MOD_WALK = 1;
    public static final int SPEED_MOD_RUN = 2;
    
    // Move directions
    public static final int MOVE_DIRECTION_UP = 1;
    public static final int MOVE_DIRECTION_DOWN = 2;
    public static final int MOVE_DIRECTION_LEFT = 3;
    public static final int MOVE_DIRECTION_RIGHT = 4;
    
    // The server the entity belongs to
    private final SwingALingServerInterface server;
    
    // The behaviour that will be executed every loop.
    protected BaseBehaviour currentBehavior = null;
    
    // The different behaviours for different situations
    protected BaseBehaviour idleBehaviour = null;
    
    public SwingALingEntity(SwingALingServerInterface server, int x, int y, int width, int height, Color color, int visionRadius, int moveSpeed) {
        this.server = server;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visionRadius = visionRadius;
        this.moveSpeed = moveSpeed;
        
        timeListenerList = new CopyOnWriteArrayList<>();
        
        registerListener(new TimeListener(500, this, true) {
            
            @Override
            protected void onDurationPassed() {
                onThink();
            }
        });
    }
    
    public void onLoop(long timeSinceLastLoop) {
        //System.out.println("looping: "+timeSinceLastLoop);
        timeListenerList.stream().forEach((listener) -> {
            //System.out.println("js 1: "+timeSinceLastLoop);
            listener.onTimePassed(timeSinceLastLoop);
        });
        //System.out.println("js 2: "+timeSinceLastLoop);
        if (currentBehavior != null) {
            currentBehavior.executeBehaviour(timeSinceLastLoop);
        }
        //System.out.println("js 3: "+timeSinceLastLoop);
    }
    
    protected void onThink() {
        // this will go through things and decide what to do.
        // For now we just make the orc idle.
        if (currentBehavior != idleBehaviour) {
            currentBehavior = idleBehaviour;
        }
    }
    
    @Override
    public void draw(Graphics2D surface) {
        surface.setColor(color);
        surface.fillRect((int)x, (int)y, width, height);
    }
    
    @Override
    public final void registerListener(TimeListener listener) {
        timeListenerList.add(listener);
    }
    
    @Override
    public final void deRegisterListener(TimeListener listener) {
        timeListenerList.remove(listener);
    }
    
    public final void moveHorizontal(final int speedMod, final double distance) {
        x += (distance * speedMod);
        
        x = x < 0 ? 0 : x;
        x = x > SwingALingGUI.AREA_WIDTH - width ? SwingALingGUI.AREA_WIDTH - width : x;
    }
    
    public final void moveVertical(final int speedMod, final double distance) {
        y += (distance * speedMod);
        
        y = y < 0 ? 0 : y;
        y = y > SwingALingGUI.AREA_HEIGHT - height ? SwingALingGUI.AREA_HEIGHT - height : y;
    }
    
    public void setCurrentBehaviour(BaseBehaviour behaviour) {
        currentBehavior = behaviour;
    }
    
    public void clearCurrentBehaviour() {
        currentBehavior = null;
    }
    
    public double getMoveSpeed() {
        return moveSpeed;
    }
    
    public void setIdleBehaviour(BaseBehaviour newIdleBehaviour) {
        this.idleBehaviour = newIdleBehaviour;
    }
    
    public EntityCoordinates getEntityCoordinates() {
        return new EntityCoordinates(x, y);
    }
}
