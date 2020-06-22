package com.lgb.instructions.math.neg;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//negate float
public class FNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float val = stack.popFloat();
        stack.pushDouble(-val);
    }

}
