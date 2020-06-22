package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class FLOAD_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Float val = frame.localVariables.getFloat(3);
        frame.operandStack.pushFloat(val);
    }

}

