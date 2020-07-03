package com.lgb.instructions.loads.iload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ILOAD_0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        int val = frame.localVariables.getInt(0);
        frame.operandStack.pushInt(val);
    }
}
