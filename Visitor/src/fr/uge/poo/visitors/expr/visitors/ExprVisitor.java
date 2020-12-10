package fr.uge.poo.visitors.expr.visitors;

import fr.uge.poo.visitors.expr.BinOp;
import fr.uge.poo.visitors.expr.Value;

/**
 * toString -> Type generic ?
 *
 */
public interface ExprVisitor<T> {
    T visitValue(Value value);

    T visitBinOp(BinOp binOp);

}