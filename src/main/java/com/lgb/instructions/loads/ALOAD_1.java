package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ALOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVariables.getRef(1);
        frame.operandStack.pushRef(ref);
    }

}
