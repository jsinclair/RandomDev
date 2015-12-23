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
public class GenericIdleBehaviour extends BaseBehaviour {
    public static final int[] DIRECTIONS = {SwingALingEntity.MOVE_DIRECTION_UP, SwingALingEntity.MOVE_DIRECTION_DOWN, SwingALingEntity.MOVE_DIRECTION_LEFT, SwingALingEntity.MOVE_DIRECTION_RIGHT}; 
    
    private final double idleDistance;
    
    private double currentDirection;
    private double currentDistance;
    
    public GenericIdleBehaviour(SwingALingEntity entity) {
        super(entity);
        
        idleDistance = entity.getMoveSpeed();
        
        currentDistance = 0;
        currentDirection = DIRECTIONS[(int)(Math.random() * DIRECTIONS.length)];
    }
    
    @Override
    public void executeBehaviour(long timeSinceLastLoop) {
        double execDistance = (idleDistance * timeSinceLastLoop) / 1000.0;
        
        while (execDistance > 0) {
            double dirMod = (currentDirection == SwingALingEntity.MOVE_DIRECTION_UP || currentDirection == SwingALingEntity.MOVE_DIRECTION_LEFT) ? -1 : 1;
            
            double remainder = 0;
            boolean newDirection = false;
            if (currentDistance + execDistance > idleDistance) {
                remainder = (currentDistance + execDistance) - idleDistance;
                
                execDistance -= remainder;
                
                newDirection = true;
                
                currentDistance = 0;
            } else {
                currentDistance += execDistance;
            }
            
            if (currentDirection == SwingALingEntity.MOVE_DIRECTION_UP || currentDirection == SwingALingEntity.MOVE_DIRECTION_DOWN) {
                entity.moveVertical(SwingALingEntity.SPEED_MOD_WALK, execDistance * dirMod);
            } else if (currentDirection == SwingALingEntity.MOVE_DIRECTION_LEFT || currentDirection == SwingALingEntity.MOVE_DIRECTION_RIGHT) {
                entity.moveHorizontal(SwingALingEntity.SPEED_MOD_WALK, execDistance * dirMod);
            }
            
            execDistance = remainder;
            
            if (newDirection) {
                currentDirection = DIRECTIONS[(int)(Math.random() * DIRECTIONS.length)];
            }
        }
        
        if (currentDistance == idleDistance) {
            currentDirection = DIRECTIONS[(int)(Math.random() * DIRECTIONS.length)];
            
            currentDistance = 0;
        }
    }
}
