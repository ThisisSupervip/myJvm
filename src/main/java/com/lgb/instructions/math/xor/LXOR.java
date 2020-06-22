package com.lgb.instructions.math.xor;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//boolean xor long
public class LXOR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long v1 = stack.popLong();
        long v2 = stack.popLong();
        long res = v1 ^ v2;
        stack.pushLong(res);
    }

}

