package com.lgb.instructions.stores;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class LSTORE extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
        long val = frame.operandStack.popLong();
        frame.localVariables.setLong(this.index, val);
    }
}
