package com.lgb.instructions.control;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.rtda.Frame;

public class GOTO extends BranchInstruct {
    @Override
    public void execute(Frame frame) {
        branch(frame, this.offset);
    }
}
