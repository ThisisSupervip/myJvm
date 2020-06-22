package com.lgb.instructions.conversions.l2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//convert long to double
public class L2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long l = stack.popLong();
        float f = l;
        stack.pushFloat(f);
    }

}
