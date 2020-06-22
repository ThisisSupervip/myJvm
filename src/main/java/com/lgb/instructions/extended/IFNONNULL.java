package com.lgb.instructions.extended;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.rtda.Frame;

public class IFNONNULL extends BranchInstruct {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.operandStack.popRef();
        if (ref != null) {
            branch(frame, this.offset);
        }
    }
}