/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingaling.utilities;

import java.util.Objects;

/**
 *
 * @author jamesTemp
 * @param <L>
 * @param <R>
 */
public class ValuePair<L,R> {
    private final L left;
    private final R right;
    
    public ValuePair(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    public L getLeft() {
        return left;
    }
    
    public R getRight() {
        return right;
    }
    
    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValuePair<?, ?> other = (ValuePair<?, ?>) obj;
        if (!Objects.equals(this.left, other.left)) {
            return false;
        } else if (!Objects.equals(this.right, other.right)) {
            return false;
        }
        return true;
    }
}
