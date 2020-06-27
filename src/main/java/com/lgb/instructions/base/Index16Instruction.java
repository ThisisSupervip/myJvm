package com.lgb.instructions.base;

import com.lgb.classfile.fundamental.U2;
import com.lgb.rtda.Frame;

public abstract class Index16Instruction implements Instruction {

    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint16().intValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "index=" + index +
                '}';
    }

}
