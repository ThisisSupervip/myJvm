package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ILOAD_1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        int val = frame.localVariables.getInt(1);
        frame.operandStack.pushInt(val);
    }
}
