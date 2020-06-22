package com.lgb.instructions.math.sh;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

/**
 * shift left long
 */
public class LSHL extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int shiftBits = stack.popInt() & 0x1f;
        long v1 = stack.popLong();
        long res = v1 << shiftBits;
        stack.pushLong(res);
    }

}

