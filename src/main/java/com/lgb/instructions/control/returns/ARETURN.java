package com.lgb.instructions.control.returns;


import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.methodarea.Object;

public class ARETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.thread;
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        Object ref = currentFrame.operandStack.popRef();
        invokerFrame.operandStack.pushRef(ref);
    }

}