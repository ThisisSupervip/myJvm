package com.lgb.instructions.constants;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ACONST_NULL extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushRef(null);
    }
}
