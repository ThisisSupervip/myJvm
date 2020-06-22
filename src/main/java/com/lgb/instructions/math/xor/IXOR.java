package com.lgb.instructions.math.xor;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//boolean xor int
public class IXOR extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v1 = stack.popInt();
        int v2 = stack.popInt();
        int res = v1 ^ v2;
        stack.pushInt(res);
    }

}
