package com.lgb.instructions.loads.lload;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class LLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Long val = frame.localVariables.getLong(this.index);
        frame.operandStack.pushLong(val);
    }

}
