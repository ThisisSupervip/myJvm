package com.lgb.instructions.loads.fload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class FLOAD_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Float val = frame.localVariables.getFloat(2);
        frame.operandStack.pushFloat(val);
    }

}
