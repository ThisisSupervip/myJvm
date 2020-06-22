package com.lgb.instructions.constants;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LCONST_0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushLong(0L);
    }
}
