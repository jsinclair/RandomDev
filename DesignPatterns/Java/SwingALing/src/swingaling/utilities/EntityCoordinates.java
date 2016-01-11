/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.utilities;

/**
 *
 * @author jamesTemp
 */
public class EntityCoordinates {
    public final double x, y;
    
    public final int width, height;
    
    public EntityCoordinates(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        
        this.width = width;
        this.height = height;
    }
}
