package com.lgb.instructions.stack;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DUP extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        int slot = frame.operandStack.popSlot();
        frame.operandStack.pushSlot(slot);
        frame.operandStack.pushSlot(slot);
    }
}
