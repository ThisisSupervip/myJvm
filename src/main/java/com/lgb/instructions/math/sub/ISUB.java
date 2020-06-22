package com.lgb.instructions.math.sub;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//subtract int
public class ISUB extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        int res = v1 - v2;
        stack.pushInt(res);
    }

}
