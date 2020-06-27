package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DLOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.localVariables.getDouble(1);
        frame.operandStack.pushDouble(val);
    }

}
