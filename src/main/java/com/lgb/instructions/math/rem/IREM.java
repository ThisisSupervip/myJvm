package com.lgb.instructions.math.rem;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

//remainder int
public class IREM extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        if (v2 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int res = v1 % v2;
        stack.pushInt(res);
    }

}