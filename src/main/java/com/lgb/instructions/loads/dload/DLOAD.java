package com.lgb.instructions.loads.dload;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

public class DLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.localVariables.getDouble(this.index);
        frame.operandStack.pushDouble(val);
    }

}