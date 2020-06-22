package com.lgb.instructions.loads;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

//load reference from local variable
public class ALOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVariables.getInt(this.index);
        frame.operandStack.pushRef(ref);
    }

}
