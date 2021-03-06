package com.lgb.instructions.conversions.f2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//convert float to int
public class F2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float f = stack.popFloat();
        int i = (int) f;
        stack.pushInt(i);
    }
}
