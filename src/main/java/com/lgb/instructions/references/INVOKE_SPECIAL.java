package com.lgb.instructions.references;

import com.lgb.instructions.base.Index16Instruction;
import com.lgb.rtda.Frame;

public class INVOKE_SPECIAL extends Index16Instruction {

    // hack!
    @Override
    public void execute(Frame frame) {
        frame.operandStack.popRef();
    }
}
