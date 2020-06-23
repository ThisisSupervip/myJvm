package com.lgb.instructions.constants;

import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;

public class SIPUSH implements Instruction {

    private short val;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = reader.readInt16();
    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushInt(val);
    }

    @Override
    public String toString() {
        return "SIPUSH{" +
                "val=" + val +
                '}';
    }
}
