package com.lgb.instructions.math.neg;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//negate long
public class LNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long val = stack.popLong();
        stack.pushLong(-val);
    }

}
