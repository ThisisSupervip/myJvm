package com.lgb.instructions.stores.xastore;

import com.google.common.base.Preconditions;
import com.lgb.rtda.heap.methodarea.Object;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;

public class CASTORE extends Base {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int val = stack.popInt();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        Preconditions.checkNotNull(arrRef);
        char[] chars = arrRef.chars();
        checkIndex(chars.length, idx);
        chars[idx] = (char) val;
    }

}