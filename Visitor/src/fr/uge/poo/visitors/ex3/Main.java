package fr.uge.poo.visitors.ex3;
import fr.uge.poo.visitors.ex3.visitorsWithLambda.ExprVisitor;

import java.util.Iterator;
import java.util.regex.Pattern;


/**
 * Dans l'exercice précédent, on a réalisé le visiteur pour la méthode toString() en utilisant un seul StringBuilder. Comment adapter la classe ExprVisitor pour pouvoir faire de même ici ?
 * Quels sont les avantages et inconvénients d'écrire le code du visiteur de cette façon là par rapport au visteur classique du GOF ?
 * --> Gof
 * Avantages :
 * Inconvénients :
 * -->  De cette façon la :
 */
public class Main {
    public static void main(String[] args) {

        // EvalExprVisitor
        Iterator<String> iterator = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        Expr expr = Expr.parseExpr(iterator);
        ExprVisitor<Integer> evalVisitor = new ExprVisitor<>();
        evalVisitor
                .when(Value.class, Value::getValue)
                .when(BinOp.class, binOp -> binOp.getOperator().applyAsInt(evalVisitor.visit(binOp.getLeft()), evalVisitor.visit(binOp.getRight())));
        System.out.println(evalVisitor.visit(expr));


        System.out.println("#############################");

        // ToStringVisitor
        Iterator<String> iterator2 = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        Expr expr2 = Expr.parseExpr(iterator2);
        var sb = new StringBuilder(); // oblige à l'utilisateur a créer le SringBuilder dans le main alors que avant c'était gérer en intern
        ExprVisitor<StringBuilder> toStringVisitor = new ExprVisitor<>();
        toStringVisitor
                .when(Value.class, value -> sb.append(value.getValue()))
                .when(BinOp.class, binOp -> {
                    sb.append("(");
                    toStringVisitor.visit(binOp.getLeft()); // met dans le string builder le coté gauche (recursion)
                    sb.append(" ")
                            .append(binOp.getOpName())
                            .append(" ");
                    toStringVisitor.visit(binOp.getRight());
                    sb.append(")");
                    return sb;
                });
        System.out.println(toStringVisitor.visit(expr2));


    }
}
