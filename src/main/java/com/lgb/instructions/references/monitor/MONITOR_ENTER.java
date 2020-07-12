package com.lgb.instructions.references.monitor;

import com.lgb.instructions.base.NoOperandsInstruction;
import com.lgb.rtda.Frame;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.Objects;

public class MONITOR_ENTER extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.operandStack.popRef();
        if (Objects.isNull(ref)) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
    }
}
