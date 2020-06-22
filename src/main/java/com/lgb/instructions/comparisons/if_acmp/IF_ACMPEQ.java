package com.lgb.instructions.comparisons.if_acmp;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class IF_ACMPEQ extends BranchInstruct {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Object ref2 = stack.popRef();
        Object ref1 = stack.popRef();
        if (ref1.equals(ref2)) {
            branch(frame, this.offset);
        }
    }
}
