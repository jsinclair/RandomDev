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
public class UtilFunctions {
    public static boolean coordinatesCollide(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        double left1, left2;
        double right1, right2;
        double top1, top2;
        double bottom1, bottom2;
        
        left1 = x1;
        left2 = x2;
        
        right1 = left1 + w1;
        right2 = left2 + w2;
        
        top1 = y1;
        top2 = y2;
        
        bottom1 = top1 + h1;
        bottom2 = y2 + h2;
        
        if (bottom1 < top2)
            return false;
        if (top1 > bottom2)
            return false;
        
        if (right1 < left2)
            return false;
        if (left1 > right2)
            return false;
        
        return true;
    }
}
