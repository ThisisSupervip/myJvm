package com.lgb.instructions.conversions.l2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//convert long to int
public class L2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long l = stack.popLong();
        int i = (int) l;
        stack.pushInt(i);
    }
}
