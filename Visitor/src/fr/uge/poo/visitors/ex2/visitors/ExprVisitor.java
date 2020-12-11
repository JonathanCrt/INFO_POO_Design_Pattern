package fr.uge.poo.visitors.ex2.visitors;

import fr.uge.poo.visitors.ex2.BinOp;
import fr.uge.poo.visitors.ex2.Value;

/**
 * toString -> Type generic ?
 *
 */
public interface ExprVisitor<T> {
    T visitValue(Value value);

    T visitBinOp(BinOp binOp);

}