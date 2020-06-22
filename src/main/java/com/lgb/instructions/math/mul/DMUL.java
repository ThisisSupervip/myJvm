package com.lgb.instructions.math.mul;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//Multiply double
public class DMUL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();
        double res = v1 * v2;
        stack.pushDouble(res);
    }

}
