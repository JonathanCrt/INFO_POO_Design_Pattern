package fr.uge.poo.visitors.ex2;

import fr.uge.poo.visitors.ex2.visitors.EvalExprVisitor;
import fr.uge.poo.visitors.ex2.visitors.ToStringVisitor;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Calculator {
    public static void main(String[] args) {

        Iterator<String> iterator = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        EvalExprVisitor visitorContextEval = new EvalExprVisitor();
        Expr expr = Expr.parseExpr(iterator);
        System.out.println(expr.accept(visitorContextEval));

        System.out.println("##############################");

        Iterator<String> iterator2 = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        ToStringVisitor visitorContextToString = new ToStringVisitor();
        Expr expr2 = Expr.parseExpr(iterator2);
        expr2.accept(visitorContextToString);
        System.out.println(visitorContextToString);


    }
}
