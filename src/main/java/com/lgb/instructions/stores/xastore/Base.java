package com.lgb.instructions.stores.xastore;

import com.lgb.instructions.base.NoOperandsInstruction;

public abstract class Base extends NoOperandsInstruction {

    protected void checkIndex(int arrLen, int idx) {
        if (idx < 0 || idx >= arrLen) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
