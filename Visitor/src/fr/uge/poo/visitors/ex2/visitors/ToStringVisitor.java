package fr.uge.poo.visitors.ex2.visitors;

import fr.uge.poo.visitors.ex2.BinOp;
import fr.uge.poo.visitors.ex2.Value;

public class ToStringVisitor implements ExprVisitor<Void> {

    private final StringBuilder stringBuilder = new StringBuilder();

    // method return void
    @Override
    public Void visitValue(Value value) {
        stringBuilder.append(value.getValue());
        return null;
    }

    @Override
    public Void visitBinOp(BinOp binOp) {
        // "(" + left + ' ' + opName + ' ' + right + ')'
        stringBuilder
                .append("(");
        binOp.getLeft().accept(this); // met dans le string builder le coté gauche (recursion)
        stringBuilder
                .append(" ")
                .append(binOp.getOpName())
                .append(" ");
        binOp.getRight().accept(this);
        stringBuilder
                .append(")");
        return null;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
