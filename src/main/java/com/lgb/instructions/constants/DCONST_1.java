package com.lgb.instructions.constants;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DCONST_1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushDouble(1.0);
    }
}
