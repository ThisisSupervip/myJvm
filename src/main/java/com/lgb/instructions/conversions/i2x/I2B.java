package com.lgb.instructions.conversions.i2x;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//Convert int to byte
public class I2B extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int i = stack.popInt();
        int b = (byte) i;
        stack.pushInt(b);
    }

}
