package com.lgb.instructions.loads.aload;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.methodarea.Object;

public class ALOAD_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVariables.getRef(1);
        frame.operandStack.pushRef(ref);
    }

}
