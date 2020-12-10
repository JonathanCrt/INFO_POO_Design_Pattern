package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.visitors.ExprVisitor;

public class Value implements Expr {
    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitValue(this);
    }

    /*
    @Override
    public int eval() {
        return  value;
    }
     */

    /*
    @Override
    public String toString() {
        return Integer.toString(value);
    }
     */

}
