package fr.uge.poo.visitors.ex2.visitors;

import fr.uge.poo.visitors.ex2.BinOp;
import fr.uge.poo.visitors.ex2.Value;

public class EvalExprVisitor implements ExprVisitor<Integer> {

    @Override
    public Integer visitValue(Value value) {
        return value.getValue();
    }

    @Override
    public Integer visitBinOp(BinOp binOp) {
        // parcourir l'arbre d'expression à gauche et a droite.
        return binOp.getOperator().applyAsInt(binOp.getLeft().accept(this), binOp.getRight().accept(this));
    }
}
