package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class DLOAD_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.localVariables.getDouble(3);
        frame.operandStack.pushRef(val);
    }

}
