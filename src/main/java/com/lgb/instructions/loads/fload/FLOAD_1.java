package com.lgb.instructions.loads.fload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class FLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Float val = frame.localVariables.getFloat(1);
        frame.operandStack.pushFloat(val);
    }

}
