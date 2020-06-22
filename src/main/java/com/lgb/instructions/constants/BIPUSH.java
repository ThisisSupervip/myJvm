package com.lgb.instructions.constants;

import com.lgb.instructions.base.BytecodeReader;
import com.lgb.instructions.base.Instruction;
import com.lgb.rtda.Frame;

public class BIPUSH implements Instruction {
    private byte val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushInt(val);
    }
}
