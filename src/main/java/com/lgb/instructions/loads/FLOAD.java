package com.lgb.instructions.loads;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;

//load float from local variable
public class FLOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Float val = frame.localVariables.getFloat(this.index);
        frame.operandStack.pushFloat(val);
    }

}
