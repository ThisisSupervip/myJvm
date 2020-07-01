package com.lgb.instructions.control.returns;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;

public class RETURN extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.thread.popFrame();
    }
}
