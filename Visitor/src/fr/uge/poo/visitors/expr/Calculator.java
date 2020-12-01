package fr.uge.poo.visitors.expr;

import fr.uge.poo.visitors.expr.visitors.EvalExprVisitor;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Calculator {
    public static void main(String[] args) {
        var visitor = new EvalExprVisitor();

        Iterator<String> it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        Expr expr = Expr.parseExpr(it);
        System.out.println(expr);
        System.out.println(expr.accept(visitor));
    }
}
