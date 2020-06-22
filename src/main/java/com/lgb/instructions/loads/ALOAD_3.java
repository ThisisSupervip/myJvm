package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ALOAD_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVariables.getRef(3);
        frame.operandStack.pushRef(ref);
    }

}
