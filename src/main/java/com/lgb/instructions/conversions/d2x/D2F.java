package com.lgb.instructions.conversions.d2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class D2F extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double d = stack.popDouble();
        float f = (float) d;
        stack.pushFloat(f);
    }
}
