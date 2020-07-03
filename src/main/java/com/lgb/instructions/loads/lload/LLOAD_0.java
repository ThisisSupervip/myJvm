package com.lgb.instructions.loads.lload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LLOAD_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Long val = frame.localVariables.getLong(0);
        frame.operandStack.pushLong(val);
    }

}
