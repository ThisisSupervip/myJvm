package com.lgb.instructions.constants;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ICONST_3 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.operandStack.pushInt(3);
    }
}
