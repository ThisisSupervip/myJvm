package com.lgb.instructions.math.neg;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//negate double
public class DNEG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double val = stack.popDouble();
        stack.pushDouble(-val);
    }

}