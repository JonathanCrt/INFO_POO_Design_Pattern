package fr.uge.poo.visitors.expr.visitorsLambda;

import fr.uge.poo.visitors.expr.Expr;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 *
 * Quelle doit être la signature de la méthode when ? =>
 * Quelle est la signature de la méthode visit ? =>
 * Quelle structure de données doit être utilisée pour implanter les méthodes when et visit ? => map
 * @param <T>
 */
public class ExprVisitor<T, U> {

    private final HashMap<Class<? extends Expr>, BiFunction<Expr, U, T>> classFunctionHashMap =  new HashMap<>();

    public <E extends Expr> ExprVisitor<T, U> when(Class<E> className, BiFunction<E, U, T> function){
        Objects.requireNonNull(className);
        Objects.requireNonNull(function);
        if(classFunctionHashMap.containsKey(className)) {
            throw new IllegalStateException(className.getName() + " is already defined");
        }
        this.classFunctionHashMap.put(className, (BiFunction<Expr, U, T>) function);
        return this;
    }

    public T visit(Expr expr, U context)  {
        var function = classFunctionHashMap.get(expr.getClass());
        if (function == null) {
            throw new IllegalStateException(expr.getClass().getName() + " is not defined");
        }
        return function.apply(expr, context);
    }
}
