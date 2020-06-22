package com.lgb.instructions.math.sh;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

/**
 * shift right int
 */
public class ISHR extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int shiftBits = stack.popInt() & 0x1f;
        int i = stack.popInt();
        int result = i >> shiftBits;
        stack.pushInt(result);
    }
}
