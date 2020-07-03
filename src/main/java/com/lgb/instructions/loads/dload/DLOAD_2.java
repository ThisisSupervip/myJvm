package com.lgb.instructions.loads.dload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DLOAD_2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.localVariables.getDouble(2);
        frame.operandStack.pushDouble(val);
    }

}

