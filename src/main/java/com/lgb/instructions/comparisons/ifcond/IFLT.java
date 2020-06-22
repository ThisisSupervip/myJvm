package com.lgb.instructions.comparisons.ifcond;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.rtda.Frame;

public class IFLT extends BranchInstruct {

    @Override
    public void execute(Frame frame) {
        int val = frame.operandStack.popInt();
        if (val < 0) {
            branch(frame, this.offset);
        }
    }
}
