package fr.uge.poo.visitors.expr.visitors;

import fr.uge.poo.visitors.expr.BinOp;
import fr.uge.poo.visitors.expr.Value;

public class ToStringVisitor implements ExprVisitor<String> {

    @Override
    public String visitValue(Value value) {
        return value.toString();
    }

    @Override
    public String visitBinOp(BinOp binOp) {
        return binOp.toString();
    }
}
