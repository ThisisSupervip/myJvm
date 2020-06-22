package com.lgb.instructions.stack;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class POP extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.operandStack.popSlot();
    }
}
