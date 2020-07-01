package com.lgb.instructions.control.returns;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;

public class IRETURN extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        Thread thread = frame.thread;
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        int val = currentFrame.operandStack.popInt();
        invokerFrame.operandStack.pushInt(val);
    }
}
