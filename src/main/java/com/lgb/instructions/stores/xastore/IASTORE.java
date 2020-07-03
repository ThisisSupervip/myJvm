package com.lgb.instructions.stores.xastore;

import com.google.common.base.Preconditions;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class IASTORE extends Base {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val = stack.popInt();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        Preconditions.checkNotNull(arrRef);
        int[] ints = arrRef.ints();
        checkIndex(ints.length, idx);
        ints[idx] = val;

    }

}
