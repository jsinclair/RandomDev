/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.entities;

import swingaling.entities.components.TimeListenerController;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
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
    
    private final EntityFactions faction;
    
    // The behaviour that will be executed every loop.
    protected BaseBehaviour currentBehavior = null;
    
    // The different behaviours for different situations
    protected BaseBehaviour idleBehaviour = null;
    
    private boolean alert = false;
    
    public SwingALingEntity(SwingALingServerInterface server, int x, int y, int width, int height, Color color, int visionRadius, int moveSpeed, EntityFactions faction) {
        this.server = server;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visionRadius = visionRadius;
        this.moveSpeed = moveSpeed;
        this.faction = faction;
        
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
        //System.out.println("this: "+this.getClass().getName());
        List<SwingALingEntity> visibleEntities = new LinkedList<>();
        server.getEntities().stream().forEach((entity) -> {
            if (!entity.equals(this)) {
                if (canSeeCoordinates(entity.getEntityCoordinates())) {
                    visibleEntities.add(entity);
                }
            }
        });
        
        alert = visibleEntities.size() > 0;
        
        if (currentBehavior != idleBehaviour) {
            currentBehavior = idleBehaviour;
        }
    }
    
    @Override
    public void draw(Graphics2D surface) {
        surface.setColor(color);
        surface.fillRect((int)x, (int)y, width, height);
        
        if (alert) {
            surface.setColor(Color.yellow);
            surface.drawString("!", (int)x + width / 2, (int)y - 0x2);
        }
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
        return new EntityCoordinates(x, y, width, height);
    }
    
    public boolean canSeeCoordinates(EntityCoordinates coords) {
        // Start by getting the center point of this entity:
        double centerX = x + width/2;
        double centerY = y + height/2;
        
        // Check that the other entity isnt on top of this one
        if ((centerX > coords.x && centerX < coords.x + coords.width) && 
                (centerY > coords.y && centerY < coords.y + coords.height)) {
            return true;
        }
        
        // Then we need to calculate the closest point in the coordinates 
        // to the entity.
        double xPoint = coords.x + coords.width/2;
        double yPoint = coords.y + coords.height/2;
        
        // Then see if that falls inside this entity's vision radius.
        /*System.out.println("visionRadius: "+visionRadius);
        System.out.println("xPoint: "+xPoint);
        System.out.println("yPoint: "+yPoint);
        System.out.println("centerX: "+centerX);
        System.out.println("centerY: "+centerY);
        double theD = (xPoint - centerX) * (xPoint - centerX) 
                + (yPoint - centerY) * (yPoint - centerY);
        System.out.println("theD: "+theD);
        System.out.println("d: "+Math.sqrt(theD));
        System.out.println("d2: "+Math.sqrt((xPoint - centerX) * (xPoint - centerX) 
                + (yPoint - centerY) * (yPoint - centerY)));*/
        
        return visionRadius * visionRadius >= (xPoint - centerX) * (xPoint - centerX) 
                + (yPoint - centerY) * (yPoint - centerY);
    }
}
