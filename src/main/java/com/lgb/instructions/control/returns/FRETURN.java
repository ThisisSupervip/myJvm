package com.lgb.instructions.control.returns;


import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;

public class FRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.thread;
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        float val = currentFrame.operandStack.popFloat();
        invokerFrame.operandStack.pushFloat(val);
    }

}
