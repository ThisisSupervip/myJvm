package com.lgb.instructions.loads.iload;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class ILOAD extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
        int val = frame.localVariables.getInt(this.index);
        frame.operandStack.pushInt(val);
    }

}
