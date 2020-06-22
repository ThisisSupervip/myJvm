package com.lgb.instructions.loads;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class ALOAD_0 extends NoOperandsInstruction {
    
    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVariables.getRef(0);
        frame.operandStack.pushRef(ref);
    }

}
