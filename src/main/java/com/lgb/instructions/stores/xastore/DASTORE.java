package com.lgb.instructions.stores.xastore;

import com.google.common.base.Preconditions;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;


public class DASTORE extends Base {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        double val = stack.popDouble();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        Preconditions.checkNotNull(arrRef);
        double[] doubles = arrRef.doubles();
        checkIndex(doubles.length, idx);
        doubles[idx] = val;
    }

}
