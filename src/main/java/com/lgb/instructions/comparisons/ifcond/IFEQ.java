package com.lgb.instructions.comparisons.ifcond;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class IFEQ extends BranchInstruct {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val = stack.popInt();
        if(val == 0) {
            branch(frame, this.offset);
        }
    }
}
