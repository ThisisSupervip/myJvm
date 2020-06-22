package com.lgb.instructions.stack;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class POP2 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.operandStack.popSlot();
        frame.operandStack.popSlot();
    }
}
