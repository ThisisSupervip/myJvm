package com.lgb.instructions.conversions.i2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//convert int to float
public class I2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int i = stack.popInt();
        float f = i;
        stack.pushFloat(f);
    }

}
