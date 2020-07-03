package com.lgb.instructions.loads.lload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Long val = frame.localVariables.getLong(1);
        frame.operandStack.pushLong(val);
    }

}
