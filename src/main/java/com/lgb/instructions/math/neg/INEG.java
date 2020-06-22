package com.lgb.instructions.math.neg;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//negate int
public class INEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val = stack.popInt();
        stack.pushDouble(-val);
    }

}

