package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class LLOAD_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Long val = frame.localVariables.getLong(2);
        frame.operandStack.pushLong(val);
    }

}
