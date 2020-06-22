package com.lgb.instructions.base;

import com.lgb.classfile.fundamental.U2;
import com.lgb.rtda.Frame;

public abstract class Index16Instruction implements Instruction {

    private U2 index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint16();
    }

}
