package com.lgb.instructions.comparisons.if_icmp;

import com.lgb.instructions.base.BranchInstruct;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class IF_ICMPGT extends BranchInstruct {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val2 = stack.popInt();
        int val1 = stack.popInt();
        if (val1 > val2) {
            branch(frame, this.offset);
        }
    }

}