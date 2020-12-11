package fr.uge.poo.visitors.ex1;

import fr.uge.poo.visitors.ex1.visitors.STPCommandVisitor;

public class HelloCmd implements STPCommand {


    @Override
    public void accept(STPCommandVisitor visitor) {
        visitor.visit(this);
    }
}
