package com.lgb.instructions.math.div;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//divide double
public class FDIV extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();
        float res = v1 / v2;
        stack.pushFloat(res);
    }

}
