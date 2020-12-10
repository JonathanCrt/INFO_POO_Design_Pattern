package com.evilcorp.stp;

import com.evilcorp.stp.visitors.STPCommandVisitor;

public class HelloCmd implements STPCommand {


    @Override
    public void accept(STPCommandVisitor visitor) {
        visitor.visit(this);
    }
}