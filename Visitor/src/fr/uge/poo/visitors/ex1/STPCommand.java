package fr.uge.poo.visitors.ex1;

import fr.uge.poo.visitors.ex1.visitors.STPCommandVisitor;

public interface STPCommand {
    void accept(STPCommandVisitor visitor);
    // void perform();
}
