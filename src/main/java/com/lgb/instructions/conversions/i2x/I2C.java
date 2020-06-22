package com.lgb.instructions.conversions.i2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//convert int to char
public class I2C extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int i = stack.popInt();
        int c = (short) i;
        stack.pushInt(c);
    }

}