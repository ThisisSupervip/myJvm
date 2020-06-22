package com.lgb.instructions.comparisons;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

/**
 * Compare long
 */
public class LCMP extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long v2 = stack.popLong();
        long v1 = stack.popLong();
        if (v1 > v2) {
            stack.pushInt(1);
        } else if(v1 == v2) {
            stack.pushInt(0);
        } else {
            stack.pushInt(-1);
        }

    }
}
