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
public abstract class TimeListener {
    private final long duration;
    
    private final TimeListenerController controller;
    
    private final boolean reapply;
            
    protected long remainder;
    
    public TimeListener(int duration, TimeListenerController controller, boolean reapply) {
        this.duration = duration;
        this.remainder = duration;
        this.reapply = reapply;
        
        this.controller = controller;
    }
    
    public final void onTimePassed(long timePassed) {
        remainder -= timePassed;
        
        while(remainder <= 0) {
            onDurationPassed();
            
            if (reapply) {
                remainder += duration;
            } else {
                controller.deRegisterListener(this);
                break;
            }
        }
    }
    
    protected abstract void onDurationPassed();
}
