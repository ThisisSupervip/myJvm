package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LLOAD_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Long val = frame.localVariables.getLong(3);
        frame.operandStack.pushLong(val);
    }

}
