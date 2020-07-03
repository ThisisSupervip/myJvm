package com.lgb.instructions.stores.xastore;

import com.google.common.base.Preconditions;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class LASTORE extends Base {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        long val = stack.popLong();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        Preconditions.checkNotNull(arrRef);
        long[] longs = arrRef.longs();
        checkIndex(longs.length, idx);
        longs[idx] = val;
    }

}
