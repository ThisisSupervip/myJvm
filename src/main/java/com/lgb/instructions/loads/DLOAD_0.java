package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DLOAD_0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.localVariables.getDouble(0);
        frame.operandStack.pushRef(val);
    }

}