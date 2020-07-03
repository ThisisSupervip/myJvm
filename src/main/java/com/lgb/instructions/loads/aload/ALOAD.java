package com.lgb.instructions.loads.aload;

import com.lgb.instructions.base.Index8Instruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.methodarea.Object;

//load reference from local variable
public class ALOAD extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVariables.getRef(this.index);
        frame.operandStack.pushRef(ref);
    }

}
