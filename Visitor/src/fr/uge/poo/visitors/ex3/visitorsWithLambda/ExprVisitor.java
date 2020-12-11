package fr.uge.poo.visitors.ex3.visitorsWithLambda;

import fr.uge.poo.visitors.ex3.Expr;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * Quelle doit être la signature de la méthode when ? => La manipulation de l'arbre , feuille => valeur , noeud => BinOp (parcourir à gauche et à droite)
 * enresgitrer des callback qui sont des pointeurs de fonctions.
 * BiFunction -> le call back, l'action a effectuer (Ici , code des visits de EvalExprVisitor)
 * Quelle est la signature de la méthode visit ? => Elle prend un Expr car on visite l'expression
 * Quelle structure de données doit être utilisée pour implanter les méthodes when et visit ? => map de class & Function.
 * Exo 2 / Exo 3 =>
 * @param <T>
 */
public class ExprVisitor<T> {
    // => n'importe quoi qui hérite de Expr
    private final HashMap<Class<? extends Expr>, Function<Expr, T>> classFunctionHashMap =  new HashMap<>();

    public <E extends Expr> ExprVisitor<T> when(Class<E> className, Function<E, T> function){
        Objects.requireNonNull(className);
        Objects.requireNonNull(function);
        if(classFunctionHashMap.containsKey(className)) {
            throw new IllegalStateException(className.getName() + " is already defined");
        }
        this.classFunctionHashMap.put(className, (Function<Expr, T>) function);
        return this;
    }

    public T visit(Expr expr)  {
        var function = classFunctionHashMap.get(expr.getClass());
        if (function == null) {
            throw new IllegalStateException(expr.getClass().getName() + " is not defined");
        }
        return function.apply(expr);
    }
}
