package com.evilcorp.stp;

import com.evilcorp.stp.visitors.STPCommandVisitor;

public interface STPCommand {
    void accept(STPCommandVisitor visitor);
    // void perform();
}
