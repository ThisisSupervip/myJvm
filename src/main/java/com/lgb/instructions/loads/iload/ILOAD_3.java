package com.lgb.instructions.loads.iload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ILOAD_3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        int val = frame.localVariables.getInt(3);
        frame.operandStack.pushInt(val);
    }
}
