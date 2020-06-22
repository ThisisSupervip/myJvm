package com.lgb.instructions.conversions.f2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//convert float to long
public class F2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float f = stack.popFloat();
        long l = (long) f;
        stack.pushLong(l);
    }
}
