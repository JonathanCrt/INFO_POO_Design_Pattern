package fr.uge.poo.visitors.expr.visitors;

import fr.uge.poo.visitors.expr.BinOp;
import fr.uge.poo.visitors.expr.Value;

public class EvalExprVisitor<T> implements ExprVisitor<Integer> {

    @Override
    public Integer visitValue(Value value) {
        return value.getValue();
    }

    @Override
    public Integer visitBinOp(BinOp binOp) {
        return binOp.getOperator().applyAsInt(binOp.getLeft().accept(this), binOp.getRight().accept(this));
    }
}
