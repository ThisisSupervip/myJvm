package com.lgb.instructions.loads.xaload;

import com.google.common.base.Preconditions;
import com.lgb.rtda.Frame;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.heap.methodarea.Object;


public class AALOAD extends Base {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack;
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        Preconditions.checkNotNull(arrRef);
        Object[] refs = arrRef.refs();
        checkIndex(refs.length, idx);
        stack.pushRef(refs[idx]);
    }

}
