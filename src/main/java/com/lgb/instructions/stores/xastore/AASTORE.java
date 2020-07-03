package com.lgb.instructions.stores.xastore;

import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.google.common.base.Preconditions;
import com.lgb.rtda.heap.methodarea.Object;

public class AASTORE extends Base {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        Object ref = stack.popRef();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();
        Preconditions.checkNotNull(arrRef);
        Object[] refs = arrRef.refs();
        checkIndex(refs.length, idx);
        refs[idx] = ref;

    }

}
